package br.com.zupacademy.giovannimoratto.desafioproposta.aviso;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @Author giovanni.moratto
 */

public class AvisoRequest {

    @NotBlank
    private final String destino;
    @NotNull
    @Future
    private final LocalDate validoAte;

    public AvisoRequest(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public AvisoModel toModel(String ipClient, String userAgent, CartaoModel cartao) {
        return new AvisoModel(destino, validoAte, ipClient, userAgent, cartao);
    }
}
