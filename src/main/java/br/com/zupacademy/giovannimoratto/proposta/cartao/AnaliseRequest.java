package br.com.zupacademy.giovannimoratto.proposta.cartao;

/**
 * @Author giovanni.moratto
 */

public class AnaliseRequest {

    /* Attributes */
    private final String documento;
    private final String nome;
    private final String idProposta;

    /* Constructors */
    public AnaliseRequest(String documento, String nome, String idProposta) {
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