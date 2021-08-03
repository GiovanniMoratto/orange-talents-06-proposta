package br.com.zupacademy.giovannimoratto.desafioproposta.feign;

import br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio.BloqueioRequest;
import br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio.BloqueioResponse;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.AnaliseRequest;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author giovanni.moratto
 */

@FeignClient(url = "${cartao-resource}", name = "cartoes")
public interface CartoesFeignClient {

    @PostMapping("/api/cartoes")
    CartaoResponse associaCartao(@RequestBody AnaliseRequest request);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    BloqueioResponse solicitacaoBloqueio(@PathVariable("id") String id, @RequestBody BloqueioRequest request);

}
