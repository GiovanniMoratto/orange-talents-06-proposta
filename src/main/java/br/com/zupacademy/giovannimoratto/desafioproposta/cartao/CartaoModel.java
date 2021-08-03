package br.com.zupacademy.giovannimoratto.desafioproposta.cartao;

import br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio.BloqueioModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.biometria.BiometriaModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoStatus.ATIVO;
import static br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoStatus.BLOQUEADO;
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
    @JsonManagedReference
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set <BiometriaModel> biometrias = new HashSet <>();
    @JsonManagedReference
    @OneToOne(mappedBy = "cartao")
    private BloqueioModel bloqueio;
    @Enumerated(EnumType.STRING)
    private CartaoStatus status = ATIVO;

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

    /* Methods */
    public void bloquear() {
        this.status = BLOQUEADO;
    }

    /* Getters */
    public PropostaModel getProposta() {
        return proposta;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public CartaoStatus getStatus() {
        return status;
    }
}