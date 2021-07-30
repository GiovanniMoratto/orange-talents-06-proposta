package br.com.zupacademy.giovannimoratto.desafioproposta.proposta;

import br.com.zupacademy.giovannimoratto.desafioproposta.feign.SolicitacaoFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

import static br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaStatus.ELEGIVEL;
import static br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaStatus.NAO_ELEGIVEL;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class PropostaController {

    @Autowired
    private PropostaRepository repository;
    @Autowired
    private SolicitacaoFeignClient consulta;

    @PostMapping("/nova-proposta")
    @Transactional
    public ResponseEntity <?> cadastraProposta(@RequestBody @Valid PropostaRequest request,
                                               UriComponentsBuilder uriBuilder) {
        PropostaModel novaProposta = request.toModel();
        repository.save(novaProposta);
        verificaRestricao(novaProposta);
        URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(novaProposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    private void verificaRestricao(PropostaModel novaProposta) {
        try {
            consulta.verificaRestricao(novaProposta.toAnalise());
            novaProposta.adicionaRestricao(ELEGIVEL);
            repository.save(novaProposta);
        } catch (FeignException.UnprocessableEntity e) {
            novaProposta.adicionaRestricao(NAO_ELEGIVEL);
            repository.save(novaProposta);
        }
    }

    @GetMapping("/proposta/{id}")
    public ResponseEntity <PropostaResponse> buscaProposta(@PathVariable Long id) {
        PropostaModel proposta = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Proposta não encontrada."));
        return ResponseEntity.ok(new PropostaResponse(proposta));
    }

}
