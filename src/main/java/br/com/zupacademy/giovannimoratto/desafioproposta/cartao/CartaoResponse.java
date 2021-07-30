package br.com.zupacademy.giovannimoratto.desafioproposta.cartao;

import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */

public class CartaoResponse {

    /* Attributes */
    @JsonProperty("id")
    private final String numero;
    private final LocalDateTime emitidoEm;
    private final String titular;
    private final Integer limite;
    private final String idProposta;

    /* Constructors */
    public CartaoResponse(String numero, LocalDateTime emitidoEm, String titular, Integer limite, String idProposta) {
        this.numero = numero;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.idProposta = idProposta;
    }

    /* Methods */
    public CartaoModel toModel(PropostaModel proposta) {
        return new CartaoModel(numero, emitidoEm, titular, limite, proposta);
    }

}