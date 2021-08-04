package br.com.zupacademy.giovannimoratto.desafioproposta.feign.response;

/**
 * @Author giovanni.moratto
 */

public class ResultadoBloqueio {

    /* Attributes */
    private BloqueioStatus resultado;

    /* Constructors */
    @Deprecated
    public ResultadoBloqueio() {
    }

    public ResultadoBloqueio(BloqueioStatus resultado) {
        this.resultado = resultado;
    }

    /* Getters */
    public BloqueioStatus getResultado() {
        return resultado;
    }

}
