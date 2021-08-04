package br.com.zupacademy.giovannimoratto.desafioproposta.feign.response;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */

public class CartaoClientResponse {

    /* Attributes */
    @JsonProperty("id")
    private final String numero;
    private final LocalDateTime emitidoEm;
    private final String titular;
    private final Integer limite;
    private final String idProposta;

    /* Constructors */
    public CartaoClientResponse(String numero, LocalDateTime emitidoEm, String titular, Integer limite, String idProposta) {
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

    /* Getters */
    public String getNumero() {
        return numero;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public Integer getLimite() {
        return limite;
    }

    public String getIdProposta() {
        return idProposta;
    }

}