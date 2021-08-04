package br.com.zupacademy.giovannimoratto.desafioproposta.bloqueio;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoStatus;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.request.SolicitacaoBloqueio;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.response.ResultadoBloqueio;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class BloqueioController {

    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);
    private final CartaoRepository cartaoRepository;
    private final BloqueioRepository bloqueioRepository;
    private final CartoesFeignClient feignClient;

    @Autowired
    public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository,
                              CartoesFeignClient feignClient) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.feignClient = feignClient;
    }

    @PostMapping("/cartoes/bloqueio/{id}")
    @Transactional
    public ResponseEntity <?> solicitacaoDeBloqueio(@PathVariable Long id,
                                                    @RequestHeader(value = "User-Agent") String userAgent,
                                                    HttpServletRequest request) {
        CartaoModel cartao = verificaSeCartaoExiste(id);
        logger.info("Cartão encontrado");
        logger.info("Requisição de bloqueio de cartão recebida e validada");
        verificaSeCartaoJaEstaBloqueado(cartao);
        notificaBloqueio(cartao);

        BloqueioModel solicitacaoBloqueio = new BloqueioModel(request, userAgent, cartao);
        logger.info("Requisição de bloqueio convertida em classe de domínio");

        bloqueioRepository.save(solicitacaoBloqueio);
        logger.info("Bloqueio persistido no banco de dados");

        cartao.bloquear();
        logger.info("Cartão bloqueado");

        cartaoRepository.save(cartao);
        logger.info("Status do cartão atualizado no banco de dados");
        return ResponseEntity.ok().build();
    }

    private CartaoModel verificaSeCartaoExiste(Long id) {
        logger.info("Buscando cartão por ID...");

        return cartaoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Este cartão não existe"));
    }

    private void verificaSeCartaoJaEstaBloqueado(CartaoModel cartao) {
        logger.info("Verificando se o cartão já está bloqueado...");
        Optional <CartaoModel> optionalCartao = cartaoRepository.findById(cartao.getId());

        if (optionalCartao.isPresent() && optionalCartao.get().getStatus().equals(CartaoStatus.BLOQUEADO)) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Este cartão já foi bloqueado");
        }
        logger.info("Verificando se o cartão possui solicitação de bloqueio...");
        Optional <BloqueioModel> solicitacaoBloqueio = bloqueioRepository.findByCartao(cartao);

        if (solicitacaoBloqueio.isPresent() && solicitacaoBloqueio.get().isAtivo()) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Já foi solicitado o bloqueio deste cartão");
        }
        logger.info("Nenhum bloqueio ativo no cartão");
    }

    private void notificaBloqueio(CartaoModel cartao) {
        logger.info("Enviando notificação de solicitação de bloqueio do cartão ao cliente...");

        try {
            ResultadoBloqueio resultado = feignClient.notificacaoDeBloqueio(
                    cartao.getNumero(), new SolicitacaoBloqueio("api-desafio-proposta"));
            logger.info("Resultado da notificação: {}", resultado.getResultado());
        } catch (FeignException e) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Falha no sistema");
        }
    }

}

