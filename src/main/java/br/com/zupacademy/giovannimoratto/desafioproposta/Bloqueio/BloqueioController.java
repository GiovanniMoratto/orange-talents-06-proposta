package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

import static br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio.BloqueioStatus.FALHA;
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
    private CartoesFeignClient api;

    @PostMapping("/cartoes/bloqueio/{id}")
    @Transactional
    public ResponseEntity <?> bloquearCartao(@PathVariable Long id,
                                             @RequestHeader(value = "User-Agent") String userAgent,
                                             HttpServletRequest request) {

        CartaoModel cartao = verificaSeCartaoExiste(id);
        verificaSeCartaoJaEstaBloqueado(cartao);
        solicitaBloqueio(cartao);

        BloqueioModel cartaoBloqueado = new BloqueioModel(request.getRemoteAddr(), userAgent,
                cartao.getNumero(), cartao);

        bloqueioRepository.save(cartaoBloqueado);
        return ResponseEntity.ok().build();
    }

    private CartaoModel verificaSeCartaoExiste(Long id) {
        return cartaoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Este cartão não existe"));
    }

    private void verificaSeCartaoJaEstaBloqueado(CartaoModel cartao) {
        Optional <BloqueioModel> optionalBloqueio = bloqueioRepository.findByNumero(cartao.getNumero());
        if (optionalBloqueio.isPresent()) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Cartão já bloqueado");
        }
    }

    private void solicitaBloqueio(CartaoModel cartao) {
        BloqueioResponse resultado = api.solicitacaoBloqueio(
                cartao.getNumero(), new BloqueioRequest("api-proposta"));
        if (resultado.getResultado().equals(FALHA)) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Falha no sistema");
        }
    }
}

