package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

import br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio.BloqueioStatus;

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
