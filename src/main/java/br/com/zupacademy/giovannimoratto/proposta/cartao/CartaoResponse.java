package br.com.zupacademy.giovannimoratto.proposta.cartao;

import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @Author giovanni.moratto
 */

public class CartaoResponse {

    @NotBlank
    private String id;

    @NotNull
    @Positive
    private Long idProposta;


    public CartaoResponse(String id, Long idProposta) {
        this.id = id;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public CartaoModel toModel(PropostaModel proposta) {
        return new CartaoModel(id, proposta);
    }

}
