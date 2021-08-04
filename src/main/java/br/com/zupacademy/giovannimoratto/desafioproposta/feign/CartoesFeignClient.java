package br.com.zupacademy.giovannimoratto.desafioproposta.feign;

import br.com.zupacademy.giovannimoratto.desafioproposta.feign.request.NovoCartao;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.request.SolicitacaoAvisoViagem;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.request.SolicitacaoBloqueio;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.request.SolicitacaoInclusaoCarteira;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.response.CartaoClientResponse;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.response.ResultadoAvisoViagem;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.response.ResultadoBloqueio;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.response.ResultadoCarteira;
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
    CartaoClientResponse associaCartao(@RequestBody @Valid NovoCartao request);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    ResultadoBloqueio notificacaoDeBloqueio(@PathVariable("id") String id,
                                            @RequestBody @Valid SolicitacaoBloqueio request);

    @PostMapping("/api/cartoes/{id}/avisos")
    ResultadoAvisoViagem notificacaoDeAviso(@PathVariable String id,
                                            @RequestBody @Valid SolicitacaoAvisoViagem request);

    @PostMapping("/api/cartoes/{id}/carteiras")
    ResultadoCarteira cadastraCarteiraDigital(@PathVariable String id,
                                              @RequestBody @Valid SolicitacaoInclusaoCarteira request);

}
