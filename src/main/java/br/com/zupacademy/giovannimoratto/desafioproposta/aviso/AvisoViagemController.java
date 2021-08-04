package br.com.zupacademy.giovannimoratto.desafioproposta.aviso;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.request.SolicitacaoAvisoViagem;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.response.ResultadoAvisoViagem;
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
public class AvisoViagemController {

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);
    private final CartaoRepository cartaoRepository;
    private final AvisoViagemRepository avisoViagemRepository;
    private final CartoesFeignClient feignClient;

    @Autowired
    public AvisoViagemController(CartaoRepository cartaoRepository, AvisoViagemRepository avisoViagemRepository,
                                 CartoesFeignClient feignClient) {
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
        this.feignClient = feignClient;
    }

    @PostMapping("/cartoes/aviso-viagem/{id}")
    public ResponseEntity <?> cadastraAvisoViagem(@PathVariable Long id,
                                                  @RequestBody @Valid AvisoViagemRequest bodyRequest,
                                                  HttpServletRequest httpRequest) {

        CartaoModel cartao = verificaSeCartaoExiste(id);
        logger.info("Cartão encontrado");
        logger.info("Requisição de aviso de viagem recebida e validada");

        notificaAvisoDeViagem(cartao, bodyRequest);
        logger.info("Cliente notificado com sucesso");

        AvisoViagemModel novoAviso = bodyRequest.toModel(cartao, httpRequest);
        logger.info("Requisição de aviso de viagem convertida em classe de domínio");

        avisoViagemRepository.save(novoAviso);
        logger.info("Aviso de viagem persistida no banco de dados");

        return ResponseEntity.ok().build();
    }

    private CartaoModel verificaSeCartaoExiste(Long id) {
        logger.info("Buscando cartão por ID...");

        return cartaoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Este cartão não existe"));
    }

    private void notificaAvisoDeViagem(CartaoModel cartao, AvisoViagemRequest bodyRequest) {
        logger.info("Enviando notificação de aviso de viagem ao cliente ...");

        try {
            ResultadoAvisoViagem resultado = feignClient.notificacaoDeAviso(
                    cartao.getNumero(),
                    new SolicitacaoAvisoViagem(bodyRequest));
            logger.info("Resultado da notificação devolvido pelo cliente: {}", resultado.getResultado());
        } catch (FeignException e) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Falha no sistema");
        }
    }
}
