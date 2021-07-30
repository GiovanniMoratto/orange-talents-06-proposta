package br.com.zupacademy.giovannimoratto.proposta.feign;

import br.com.zupacademy.giovannimoratto.proposta.cartao.AnaliseRequest;
import br.com.zupacademy.giovannimoratto.proposta.cartao.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author giovanni.moratto
 */

@FeignClient(url = "${cartao-resource}", name = "cartoes")
public interface CartoesFeignClient {

    @PostMapping("${novo-cartao}")
    CartaoResponse associaCartao(@RequestBody AnaliseRequest request);

}
