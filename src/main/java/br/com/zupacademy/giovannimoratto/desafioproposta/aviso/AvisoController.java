package br.com.zupacademy.giovannimoratto.desafioproposta.aviso;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.responses.AvisoClientResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

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
    @Autowired
    private CartoesFeignClient feignClient;

    @PostMapping("aviso-viagem/{id}")
    public ResponseEntity <?> avisarViagem(@PathVariable Long id, @RequestBody @Valid AvisoRequest bodyRequest,
                                           HttpServletRequest httpRequest) {

        CartaoModel cartao = cartaoRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(NOT_FOUND, "Este cartão não existe."));

        notificaAvisoDeViagem(cartao, bodyRequest);
        AvisoModel novoAviso = bodyRequest.toModel(cartao, httpRequest);
        novoAviso = avisoRepository.save(novoAviso);
        return ResponseEntity.ok().build();
    }

    private void notificaAvisoDeViagem(CartaoModel cartao, AvisoRequest bodyRequest) {
        try {
            AvisoClientResponse resultado = feignClient.notificacaoDeAviso(cartao.getNumero(), bodyRequest);

        } catch (FeignException e) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Falha no sistema");
        }
    }
}
