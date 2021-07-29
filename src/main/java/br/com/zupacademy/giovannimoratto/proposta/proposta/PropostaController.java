package br.com.zupacademy.giovannimoratto.proposta.proposta;

import br.com.zupacademy.giovannimoratto.proposta.feign.ConsultaDadosCliente;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

import static br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaStatus.ELEGIVEL;
import static br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaStatus.NAO_ELEGIVEL;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class PropostaController {

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private ConsultaDadosCliente consulta;

    @PostMapping("/nova-proposta")
    @Transactional
    public ResponseEntity <PropostaResponse> cadastraProposta(@RequestBody @Valid PropostaRequest request,
                                                              UriComponentsBuilder uriBuilder) {
        PropostaModel novaProposta = request.toModel();
        repository.save(novaProposta);
        verificaRestricao(novaProposta);
        URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(novaProposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new PropostaResponse(novaProposta));
    }

    private void verificaRestricao(PropostaModel novaProposta) {
        try {
            consulta.verificaRestricao(novaProposta.enviaDados());
            novaProposta.adicionaRestricao(ELEGIVEL);
            repository.save(novaProposta);
        } catch (FeignException.UnprocessableEntity e) {
            novaProposta.adicionaRestricao(NAO_ELEGIVEL);
            repository.save(novaProposta);
        }
    }

}
