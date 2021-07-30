package br.com.zupacademy.giovannimoratto.desafioproposta.shedule;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoResponse;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

import static br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaStatus.ELEGIVEL;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

/**
 * @Author giovanni.moratto
 */

@Component
public class AssociaCartao {

    @Autowired
    private PropostaRepository repository;
    @Autowired
    private CartoesFeignClient api;

    // Gera cartão e o associa à propostas elegivéis
    @Transactional
    @Scheduled(fixedDelayString = "${scheduled-cartao.time}")
    private void associaCartao() {
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
