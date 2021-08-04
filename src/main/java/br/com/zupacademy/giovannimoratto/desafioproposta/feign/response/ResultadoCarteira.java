package br.com.zupacademy.giovannimoratto.desafioproposta.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author giovanni.moratto
 */

public class ResultadoCarteira {

    /* Attributes */
    private final CarteiraStatus resultado;
    @JsonProperty("id")
    private final String numero;

    /* Constructors */
    public ResultadoCarteira(CarteiraStatus resultado, String numero) {
        this.resultado = resultado;
        this.numero = numero;
    }

    /* Getters */
    public CarteiraStatus getResultado() {
        return resultado;
    }

    public String getNumero() {
        return numero;
    }
}
