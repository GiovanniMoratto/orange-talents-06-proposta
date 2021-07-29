package br.com.zupacademy.giovannimoratto.proposta.cartao;

/**
 * @Author giovanni.moratto
 */

public class CartaoRequest {

    private final String documento;
    private final String nome;
    private final String idProducto;

    public CartaoRequest(String documento, String nome, String idProducto) {
        this.documento = documento;
        this.nome = nome;
        this.idProducto = idProducto;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProducto() {
        return idProducto;
    }

}
