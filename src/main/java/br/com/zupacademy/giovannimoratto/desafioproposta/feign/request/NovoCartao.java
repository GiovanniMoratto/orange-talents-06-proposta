package br.com.zupacademy.giovannimoratto.desafioproposta.feign.request;

import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

public class NovoCartao {

    /* Attributes */
    @NotBlank
    private final String documento;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String idProposta;

    /* Constructors */
    public NovoCartao(String documento, String nome, String idProposta) {
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