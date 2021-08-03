package br.com.zupacademy.giovannimoratto.desafioproposta.proposta;

import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

public class AnaliseClientRequest {

    /* Attributes */
    @NotBlank
    private final String documento;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String idProposta;

    /* Constructors */
    public AnaliseClientRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    /* Getters */
    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

}