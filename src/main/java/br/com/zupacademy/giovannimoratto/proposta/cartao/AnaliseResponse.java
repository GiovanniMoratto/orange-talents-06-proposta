package br.com.zupacademy.giovannimoratto.proposta.cartao;

/**
 * @Author giovanni.moratto
 */
public class AnaliseResponse {

    private final String documento;
    private final String nome;
    private final String idProposta;
    private final AnaliseResultado resultadoSolicitacao;

    public AnaliseResponse(String documento, String nome, String idProposta,
                           AnaliseResultado resultadoSolicitacao) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
        this.resultadoSolicitacao = resultadoSolicitacao;
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

    public AnaliseResultado getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

}
