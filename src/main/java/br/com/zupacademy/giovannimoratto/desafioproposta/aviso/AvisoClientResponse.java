package br.com.zupacademy.giovannimoratto.desafioproposta.aviso;

/**
 * @Author giovanni.moratto
 */

public class AvisoClientResponse {

    private AvisoStatus resultado;

    @Deprecated
    public AvisoClientResponse() {
    }

    public AvisoClientResponse(AvisoStatus resultado) {
        this.resultado = resultado;
    }

    public AvisoStatus getResultado() {
        return resultado;
    }

}
