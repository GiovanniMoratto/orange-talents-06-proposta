package br.com.zupacademy.giovannimoratto.proposta.cartao;

/**
 * @Author giovanni.moratto
 */
public class AnaliseRequest {

    private final String documento;
    private final String nome;
    private final String idProposta;

    public AnaliseRequest(String documento, String nome, String idProposta) {
        super();
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

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