package br.com.zupacademy.giovannimoratto.desafioproposta.feign;

import br.com.zupacademy.giovannimoratto.desafioproposta.feign.request.NovoCartao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author giovanni.moratto
 */

@FeignClient(url = "${solicitacao-analise-resource}", name = "analises")
public interface SolicitacaoFeignClient {

    @PostMapping
    void verificaRestricao(@RequestBody NovoCartao request);
}
