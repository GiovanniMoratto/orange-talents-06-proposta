package br.com.zupacademy.giovannimoratto.desafioproposta.feign;

import br.com.zupacademy.giovannimoratto.desafioproposta.aviso.AvisoRequest;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.AnaliseClientRequest;
import br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio.BloqueioClientRequest;
import br.com.zupacademy.giovannimoratto.desafioproposta.aviso.AvisoClientResponse;
import br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio.BloqueioClientResponse;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author giovanni.moratto
 */

@FeignClient(url = "${cartao-resource}", name = "cartoes")
public interface CartoesFeignClient {

    @PostMapping("/api/cartoes")
    CartaoClientResponse associaCartao(@RequestBody @Valid AnaliseClientRequest request);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    BloqueioClientResponse notificacaoDeBloqueio(@PathVariable("id") String id,
                                                 @RequestBody @Valid BloqueioClientRequest request);

    @PostMapping(value = "/api/cartoes/{id}/avisos")
    AvisoClientResponse notificacaoDeAviso(@PathVariable String id, @RequestBody @Valid AvisoRequest bodyRequest);

}
