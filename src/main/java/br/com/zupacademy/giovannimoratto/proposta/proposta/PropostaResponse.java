package br.com.zupacademy.giovannimoratto.proposta.proposta;

import java.math.BigDecimal;

/**
 * @Author giovanni.moratto
 */

public class PropostaResponse {

    /* Attributes */
    private final String documento;
    private final String email;
    private final String nome;
    private final String endereco;
    private final BigDecimal salario;

    /* Constructors */
    public PropostaResponse(PropostaModel proposta) {
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
    }

    /* Getters */
    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

}