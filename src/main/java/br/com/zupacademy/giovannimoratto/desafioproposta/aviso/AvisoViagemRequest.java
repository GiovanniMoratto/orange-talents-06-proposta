package br.com.zupacademy.giovannimoratto.desafioproposta.aviso;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @Author giovanni.moratto
 */

public class AvisoViagemRequest {

    /* Attributes */
    @NotBlank
    private final String destino;
    @NotNull
    @Future
    private final LocalDate validoAte;

    /* Constructors */
    public AvisoViagemRequest(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    /* Methods */
    public AvisoViagemModel toModel(CartaoModel cartao, HttpServletRequest httpRequest) {
        String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
        String ipClient = httpRequest.getRemoteAddr();

        return new AvisoViagemModel(destino, validoAte, ipClient, userAgent, cartao);
    }

    /* Getters */
    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

}