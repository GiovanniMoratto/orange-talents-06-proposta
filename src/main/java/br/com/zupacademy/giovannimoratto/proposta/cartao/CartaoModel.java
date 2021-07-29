package br.com.zupacademy.giovannimoratto.proposta.cartao;

import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaModel;
import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_cartoes")
public class CartaoModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String numero;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;
    @JsonBackReference
    @OneToOne(mappedBy = "cartao")
    private PropostaModel proposta;

    /* Constructors */
    @Deprecated
    public CartaoModel() {
    }

    public CartaoModel(String id, PropostaModel proposta) {
        this.numero = id;
        this.proposta = proposta;
    }

    public CartaoModel(String numero, LocalDateTime emitidoEm, String titular, Integer limite, PropostaModel proposta) {
        this.numero = numero;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.proposta = proposta;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public PropostaModel getProposta() {
        return proposta;
    }

}
