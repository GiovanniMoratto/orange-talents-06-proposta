package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

/**
 * @Author giovanni.moratto
 */

public class BloqueioClientResponse {

    private BloqueioStatus resultado;

    @Deprecated
    public BloqueioClientResponse() {
    }

    public BloqueioClientResponse(BloqueioStatus resultado) {
        this.resultado = resultado;
    }

    public BloqueioStatus getResultado() {
        return resultado;
    }

}
