package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

/**
 * @Author giovanni.moratto
 */

public class BloqueioResponse {

    private BloqueioStatus resultado;

    @Deprecated
    public BloqueioResponse() {
    }

    public BloqueioResponse(BloqueioStatus resultado) {
        this.resultado = resultado;
    }

    public BloqueioStatus getResultado() {
        return resultado;
    }

}
