package br.com.zupacademy.giovannimoratto.desafioproposta.shedule;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoClientResponse;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaStatus.ELEGIVEL;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

/**
 * @Author giovanni.moratto
 */

@Component
public class AssociaCartao {

    private final Logger logger = LoggerFactory.getLogger(AssociaCartao.class);

    @Autowired
    private PropostaRepository repository;
    @Autowired
    private CartoesFeignClient feignClient;

    // Gera cartão e o associa à propostas elegivéis
    @Scheduled(fixedDelayString = "${scheduled-cartao.time}")
    private void associaCartao() {
        List <PropostaModel> propostas = repository.findByStatusAndCartao(ELEGIVEL, null);
        logger.info("{} propostas elegiveis encontradas.", propostas.size());
        propostas.forEach(proposta -> {
            try {
                CartaoClientResponse novoCartao = feignClient.associaCartao(proposta.toAnalise());
                proposta.adicionaCartao(novoCartao.toModel(proposta));
                logger.info("Proposta de ID: {} associada ao cartão de número: {}", proposta.getId(),
                        novoCartao.getNumero());
                repository.save(proposta);
            } catch (FeignException e) {
                throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Serviço indisponivel!");
            }
        });
    }
}
