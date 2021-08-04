package br.com.zupacademy.giovannimoratto.desafioproposta.shedule;

import br.com.zupacademy.giovannimoratto.desafioproposta.feign.response.CartaoClientResponse;
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
    private final PropostaRepository repository;
    private final CartoesFeignClient feignClient;

    @Autowired
    public AssociaCartao(PropostaRepository repository, CartoesFeignClient feignClient) {
        this.repository = repository;
        this.feignClient = feignClient;
    }

    // Gera cartão e o associa à propostas elegivéis
    @Scheduled(fixedDelayString = "${scheduled-cartao.time}")
    public void autoAssociaCartao() {
        logger.info("Buscando propostas...");
        List <PropostaModel> propostas = repository.findByStatusAndCartao(ELEGIVEL, null);
        logger.info("{} propostas elegiveis encontradas.", propostas.size());

        propostas.forEach(proposta -> {
            try {
                logger.info("Enviando proposta para análise...");
                CartaoClientResponse novoCartao = feignClient.associaCartao(proposta.toAnalise());
                logger.info("Novo cartão gerado");

                proposta.adicionaCartao(novoCartao.toModel(proposta));
                logger.info("Proposta de ID: {} associada ao cartão de número: {}", proposta.getId(),
                        novoCartao.getNumero());

                repository.save(proposta);
                logger.info("Proposta Atualizada");
            } catch (FeignException e) {
                throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Serviço indisponivel!");
            }
        });
    }
}
