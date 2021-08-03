package br.com.zupacademy.giovannimoratto.desafioproposta.feign;

import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.AnaliseClientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author giovanni.moratto
 */

@FeignClient(url = "${solicitacao-analise-resource}", name = "analise")
public interface SolicitacaoFeignClient {

    @PostMapping
    void verificaRestricao(@RequestBody AnaliseClientRequest request);
}
