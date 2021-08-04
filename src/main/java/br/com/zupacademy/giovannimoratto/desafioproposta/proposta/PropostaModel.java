package br.com.zupacademy.giovannimoratto.desafioproposta.proposta;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.criptografia.Encryptor;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.request.NovoCartao;

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
    @Convert(converter = Encryptor.class)
    @Column(nullable = false, unique = true)
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
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(unique = true)
    private CartaoModel cartao;

    /* Constructors */
    @Deprecated
    public PropostaModel() {
    }

    // Defini os valores do método da PropostaRequest.class em PropostaModel.class
    public PropostaModel(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    /* Methods */
    // Instância uma classe AnaliseRequest usando os dados da PropostaModel
    public NovoCartao toAnalise() {
        return new NovoCartao(documento, nome, id.toString());
    }

    // Adiciona a restrição encontrada na consulta
    public void adicionaRestricao(PropostaStatus status) {
        this.status = status;
    }

    public void adicionaCartao(CartaoModel cartao) {
        this.cartao = cartao;
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

    public PropostaStatus getStatus() {
        return status;
    }

    public CartaoModel getCartao() {
        return cartao;
    }

}