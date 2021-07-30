package br.com.zupacademy.giovannimoratto.desafioproposta.cartao;

import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaModel;
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

    public CartaoModel(String numero, LocalDateTime emitidoEm, String titular, Integer limite, PropostaModel proposta) {
        this.numero = numero;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.proposta = proposta;
    }

    public PropostaModel getProposta() {
        return proposta;
    }

}