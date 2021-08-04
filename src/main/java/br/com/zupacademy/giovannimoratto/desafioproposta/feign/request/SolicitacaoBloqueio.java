package br.com.zupacademy.giovannimoratto.desafioproposta.feign.request;

import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

public class SolicitacaoBloqueio {

    /* Attributes */
    @NotBlank
    private final String sistemaResponsavel;

    /* Constructors */
    public SolicitacaoBloqueio(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    /* Getters */
    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

}