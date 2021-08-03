package br.com.zupacademy.giovannimoratto.desafioproposta.aviso;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class AvisoController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private AvisoRepository avisoRepository;

    @PostMapping("aviso-viagem/{id}")
    public ResponseEntity <?> avisarViagem(@PathVariable Long id, @RequestBody @Valid AvisoRequest bodyRequest,
                                           HttpServletRequest httpRequest) {

        CartaoModel cartao = cartaoRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(NOT_FOUND, "Este cartão não existe."));

        AvisoModel novoAviso = bodyRequest.toModel(cartao, httpRequest);
        novoAviso = avisoRepository.save(novoAviso);
        return ResponseEntity.ok().build();
    }
}
