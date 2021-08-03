package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

/**
 * @Author giovanni.moratto
 */

public class BloqueioClientRequest {

    private final String sistemaResponsavel;

    public BloqueioClientRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
