package br.com.zupacademy.giovannimoratto.proposta.feign;

import br.com.zupacademy.giovannimoratto.proposta.cartao.AnaliseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author giovanni.moratto
 */

@Service
@FeignClient(url = "${solicitacao-analise-resource}", name = "analise")
public interface ConsultaDadosCliente {

    @PostMapping
    void verificaRestricao(@RequestBody AnaliseRequest request);
}
