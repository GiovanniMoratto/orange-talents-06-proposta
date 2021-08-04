package br.com.zupacademy.giovannimoratto.desafioproposta.proposta;

import br.com.zupacademy.giovannimoratto.desafioproposta.feign.SolicitacaoFeignClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);
    private final PropostaRepository repository;
    private final SolicitacaoFeignClient feignClient;

    @Autowired
    public PropostaController(PropostaRepository repository, SolicitacaoFeignClient feignClient) {
        this.repository = repository;
        this.feignClient = feignClient;
    }

    @PostMapping("/nova-proposta")
    @Transactional
    public ResponseEntity <?> cadastraProposta(@RequestBody @Valid PropostaRequest request,
                                               UriComponentsBuilder uriBuilder) {
        logger.info("Requisição de proposta recebida e validada");

        PropostaModel novaProposta = request.toModel();
        logger.info("Requisição de proposta convertida em classe de domínio");

        repository.save(novaProposta);
        logger.info("Proposta persistida no banco de dados");

        verificaRestricao(novaProposta);
        logger.info("Status persistido no banco de dados");

        URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(novaProposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    private void verificaRestricao(PropostaModel novaProposta) {
        logger.info("Enviando requisição de proposta ao cliente...");
        try {
            feignClient.verificaRestricao(novaProposta.toAnalise());
            logger.info("Verificando status da requisição de proposta...");

            novaProposta.adicionaRestricao(ELEGIVEL);
            logger.info("Proposta elegivel");

            repository.save(novaProposta);
        } catch (FeignException.UnprocessableEntity e) {
            novaProposta.adicionaRestricao(NAO_ELEGIVEL);
            logger.info("Proposta não elegivel");

            repository.save(novaProposta);
        }
    }

    @GetMapping("/proposta/{id}")
    public ResponseEntity <PropostaResponse> buscaProposta(@PathVariable Long id) {
        logger.info("Buscando proposta por ID...");

        PropostaModel proposta = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Proposta não encontrada."));
        logger.info("Proposta de ID: {} encontrada.", proposta.getId());

        return ResponseEntity.ok(new PropostaResponse(proposta));
    }

}
