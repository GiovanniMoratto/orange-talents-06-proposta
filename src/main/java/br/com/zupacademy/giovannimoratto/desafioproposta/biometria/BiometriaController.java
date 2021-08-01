package br.com.zupacademy.giovannimoratto.desafioproposta.biometria;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
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

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private BiometriaRepository repository;

    @PostMapping("/nova-biometria/{id}")
    @Transactional
    public ResponseEntity <?> cadastraBiometria(@PathVariable("id") Long id,
                                                @RequestBody @Valid BiometriaRequest request,
                                                UriComponentsBuilder uriBuilder) {

        CartaoModel cartao = cartaoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Este cartão não existe"));

        BiometriaModel novaBiometria = request.toModel(cartao);
        repository.save(novaBiometria);
        URI uri = uriBuilder.path("/biometria/{id}").buildAndExpand(novaBiometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}