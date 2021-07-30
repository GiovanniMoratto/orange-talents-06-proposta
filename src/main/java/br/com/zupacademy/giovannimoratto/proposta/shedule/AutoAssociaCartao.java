package br.com.zupacademy.giovannimoratto.proposta.shedule;

import br.com.zupacademy.giovannimoratto.proposta.cartao.CartaoResponse;
import br.com.zupacademy.giovannimoratto.proposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaModel;
import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

import static br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaStatus.ELEGIVEL;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

/**
 * @Author giovanni.moratto
 */

@Component
public class AutoAssociaCartao {

    @Autowired
    private PropostaRepository repository;
    @Autowired
    private CartoesFeignClient api;

    @Scheduled(fixedDelayString = "${scheduled-cartao.time}")
    @Transactional
    private void consultaPropostasElegiveis() {
        List <PropostaModel> propostas = repository.findByStatusAndCartao(ELEGIVEL, null);
        propostas.forEach(proposta -> {
            try {
                CartaoResponse novoCartao = api.associaCartao(proposta.toAnalise());
                proposta.adicionaCartao(novoCartao.toModel(proposta));
                repository.save(proposta);
            } catch (FeignException e) {
                throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Serviço indisponivel!");
            }
        });
    }
}
