package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

/**
 * @Author giovanni.moratto
 */

public class BloqueioRequest {

    private final String sistemaResponsavel;

    public BloqueioRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
