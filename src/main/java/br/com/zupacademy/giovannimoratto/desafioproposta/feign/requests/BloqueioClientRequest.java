package br.com.zupacademy.giovannimoratto.desafioproposta.feign.requests;

import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

public class BloqueioClientRequest {

    @NotBlank
    private final String sistemaResponsavel;

    public BloqueioClientRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
