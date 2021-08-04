package br.com.zupacademy.giovannimoratto.desafioproposta.biometria;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
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
    private final CartaoRepository cartaoRepository;
    private final BiometriaRepository biometriaRepository;

    @Autowired
    public BiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping("/cartoes/nova-biometria/{id}")
    @Transactional
    public ResponseEntity <?> cadastraBiometria(@PathVariable("id") Long id,
                                                @RequestBody @Valid BiometriaRequest request,
                                                UriComponentsBuilder uriBuilder) {
        CartaoModel cartao = verificaSeCartaoExiste(id);
        logger.info("Cartão encontrado");
        logger.info("Requisição de biometria recebida e validada");

        BiometriaModel novaBiometria = request.toModel(cartao);
        logger.info("Requisição de biometria convertida em classe de domínio");

        biometriaRepository.save(novaBiometria);
        logger.info("Biometria persistida no banco de dados");

        URI uri = uriBuilder.path("/biometria/{id}").buildAndExpand(novaBiometria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    private CartaoModel verificaSeCartaoExiste(Long id) {
        logger.info("Buscando cartão por ID...");

        return cartaoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Este cartão não existe"));
    }

}