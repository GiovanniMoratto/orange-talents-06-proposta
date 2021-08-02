package br.com.zupacademy.giovannimoratto.desafioproposta.biometria;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaController;
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

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class BiometriaController {

    private final Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private BiometriaRepository repository;

    @PostMapping("/nova-biometria/{id}")
    @Transactional
    public ResponseEntity <?> cadastraBiometria(@PathVariable("id") Long id,
                                                @RequestBody @Valid BiometriaRequest request,
                                                UriComponentsBuilder uriBuilder) {
        logger.info("Buscando cartão por ID...");
        CartaoModel cartao = cartaoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Este cartão não existe"));
        logger.info("Cartão encontrado. ID: {}", cartao.getId());
        logger.info("Requisição de biometria recebida e validada");
        BiometriaModel novaBiometria = request.toModel(cartao);
        logger.info("Requisição de biometria convertida em classe de dominio");
        repository.save(novaBiometria);
        logger.info("Requisição de biometria persistida no banco de dados");
        URI uri = uriBuilder.path("/biometria/{id}").buildAndExpand(novaBiometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}