package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoStatus;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.requests.BloqueioClientRequest;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.responses.BloqueioClientResponse;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaController;
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

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private BloqueioRepository bloqueioRepository;
    @Autowired
    private CartoesFeignClient feignClient;

    @PostMapping("/cartoes/bloqueio/{id}")
    @Transactional
    public ResponseEntity <?> solicitacaoDeBloqueio(@PathVariable Long id,
                                                    @RequestHeader(value = "User-Agent") String userAgent,
                                                    HttpServletRequest request) {

        CartaoModel cartao = verificaSeCartaoExiste(id);
        logger.info("Cartão encontrado");
        verificaSeCartaoJaEstaBloqueado(cartao);
        notificaBloqueio(cartao);

        BloqueioModel solicitacaoBloqueio = new BloqueioModel(request.getRemoteAddr(), userAgent,
                cartao.getNumero(), cartao);
        logger.info("Requisição de bloqueio convertida em classe de dominio");

        bloqueioRepository.save(solicitacaoBloqueio);
        logger.info("Requisição de bloqueio persistida no banco de dados");

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
        logger.info("Notificação de tentativa de bloqueio enviada ao cliente");
        try {
            BloqueioClientResponse resultado = feignClient.notificacaoDeBloqueio(
                    cartao.getNumero(), new BloqueioClientRequest("api-proposta"));
            logger.info("Resultado da notificação: {}", resultado.getResultado());
        } catch (FeignException e) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Falha no sistema");
        }
    }

}

