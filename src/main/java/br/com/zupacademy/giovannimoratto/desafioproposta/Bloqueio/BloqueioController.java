package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
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

    @PostMapping("/cartoes/bloqueio/{id}")
    @Transactional
    public ResponseEntity <?> bloquearCartao(@PathVariable Long id,
                                             @RequestHeader(value = "User-Agent") String userAgent,
                                             HttpServletRequest request) {

        CartaoModel cartao = verificaSeCartaoExiste(id);
        logger.info("Cartão encontrado");
        verificaSeCartaoJaEstaBloqueado(cartao);
        logger.info("Nenhum bloqueio ativo no cartão");
        BloqueioModel cartaoBloqueado = new BloqueioModel(request.getRemoteAddr(), userAgent,
                cartao.getNumero(), cartao);
        logger.info("Requisição de bloqueio convertida em classe de dominio");
        bloqueioRepository.save(cartaoBloqueado);
        logger.info("Requisição de bloqueio persistida no banco de dados");
        return ResponseEntity.ok().build();
    }

    private CartaoModel verificaSeCartaoExiste(Long id) {
        logger.info("Buscando cartão por ID...");
        return cartaoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Este cartão não existe"));
    }

    private void verificaSeCartaoJaEstaBloqueado(CartaoModel cartao) {
        logger.info("Verificando se o cartão já está bloqueado...");
        Optional <BloqueioModel> optionalBloqueio = bloqueioRepository.findByNumero(cartao.getNumero());
        if (optionalBloqueio.isPresent()) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Cartão já está bloqueado");
        }
    }

}

