package br.com.zupacademy.giovannimoratto.desafioproposta.feign.responses;

/**
 * @Author giovanni.moratto
 */

public class AvisoClientResponse {

    private String resultado;

    @Deprecated
    public AvisoClientResponse() {
    }

    public AvisoClientResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
