package br.com.zupacademy.giovannimoratto.proposta.proposta;

import br.com.zupacademy.giovannimoratto.proposta.cartao.AnaliseRequest;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_propostas")
public class PropostaModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String documento;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = false)
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private PropostaStatus status;

    /* Constructors */
    @Deprecated
    public PropostaModel() {
    }

    // Set PropostaRequest.class method values in PropostaModel.class
    public PropostaModel(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    /* Methods */
    public AnaliseRequest enviaDados() {
        return new AnaliseRequest(documento, nome, id.toString());
    }

    public void adicionaRestricao(PropostaStatus status) {
        this.status = status;
    }

    /* Getters */
    public Long getId() {
        return id;
    }

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