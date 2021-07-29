package br.com.zupacademy.giovannimoratto.proposta.shedule;

import br.com.zupacademy.giovannimoratto.proposta.cartao.CartaoResponse;
import br.com.zupacademy.giovannimoratto.proposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaModel;
import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaStatus.ELEGIVEL;
import static br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaStatus.NAO_ELEGIVEL;

/**
 * @Author giovanni.moratto
 */

@Component
public class AutoAssociaCartao {

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private CartoesFeignClient cartao;

    @Scheduled(fixedDelayString = "${scheduled-cartao.time}")
    @Transactional
    private void consultaPropostasElegiveis() {
        List <PropostaModel> proposta = repository.findByStatus(ELEGIVEL);
        try {
            for (PropostaModel lista : proposta) {
                CartaoResponse novoCartao = cartao.associaCartao(lista.getId().toString());
                lista.adicionaCartao(novoCartao.toModel(lista));
                lista.adicionaRestricao(NAO_ELEGIVEL);
                repository.save(lista);
            }
        } catch (FeignException.UnprocessableEntity e) {
            e.printStackTrace();
        }
    }
}
