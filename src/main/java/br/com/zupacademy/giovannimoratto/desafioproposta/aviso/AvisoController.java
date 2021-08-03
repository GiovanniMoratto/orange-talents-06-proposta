package br.com.zupacademy.giovannimoratto.desafioproposta.aviso;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaController;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private AvisoRepository avisoRepository;
    @Autowired
    private CartoesFeignClient feignClient;

    @PostMapping("aviso-viagem/{id}")
    public ResponseEntity <?> avisarViagem(@PathVariable Long id, @RequestBody @Valid AvisoRequest bodyRequest,
                                           HttpServletRequest httpRequest) {

        CartaoModel cartao = verificaSeCartaoExiste(id);
        logger.info("Cartão encontrado");
        notificaAvisoDeViagem(cartao, bodyRequest);
        AvisoModel novoAviso = bodyRequest.toModel(cartao, httpRequest);
        logger.info("Requisição de aviso de viagem convertida em classe de dominio");
        novoAviso = avisoRepository.save(novoAviso);
        logger.info("Aviso de viagem persistida no banco de dados");
        return ResponseEntity.ok().build();
    }

    private CartaoModel verificaSeCartaoExiste(Long id) {
        logger.info("Buscando cartão por ID...");
        return cartaoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Este cartão não existe"));
    }

    private void notificaAvisoDeViagem(CartaoModel cartao, AvisoRequest bodyRequest) {
        logger.info("Notificação de aviso de viagem enviada ao cliente");
        try {
            AvisoClientResponse resultado = feignClient.notificacaoDeAviso(cartao.getNumero(), bodyRequest);
            logger.info("Resultado da notificação: {}", resultado.getResultado());
        } catch (FeignException e) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Falha no sistema");
        }
    }
}
