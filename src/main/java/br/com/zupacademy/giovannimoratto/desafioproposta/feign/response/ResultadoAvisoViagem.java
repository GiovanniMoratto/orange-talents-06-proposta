package br.com.zupacademy.giovannimoratto.desafioproposta.feign.response;

/**
 * @Author giovanni.moratto
 */

public class ResultadoAvisoViagem {

    /* Attributes */
    private AvisoStatus resultado;

    /* Constructors */
    @Deprecated
    public ResultadoAvisoViagem() {
    }

    public ResultadoAvisoViagem(AvisoStatus resultado) {
        this.resultado = resultado;
    }

    /* Getters */
    public AvisoStatus getResultado() {
        return resultado;
    }

}
