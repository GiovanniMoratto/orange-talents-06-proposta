package br.com.zupacademy.giovannimoratto.proposta.cartao;

import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */

public class CartaoResponse {

    @JsonProperty("id")
    private final String numero;
    private final Long idProposta;

    public CartaoResponse(String numero, Long idProposta) {
        this.numero = numero;
        this.idProposta = idProposta;
    }

    public String getNumero() {
        return numero;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public CartaoModel toModel(PropostaModel proposta) {
        return new CartaoModel(numero, proposta);
    }

}
