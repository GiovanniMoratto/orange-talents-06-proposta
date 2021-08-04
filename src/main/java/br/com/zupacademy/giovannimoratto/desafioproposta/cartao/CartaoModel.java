package br.com.zupacademy.giovannimoratto.desafioproposta.cartao;

import br.com.zupacademy.giovannimoratto.desafioproposta.biometria.BiometriaModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.proposta.PropostaModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @OneToMany(mappedBy = "cartao")
    private Set <BiometriaModel> biometrias;
    @Enumerated(EnumType.STRING)
    private CartaoStatus status = ATIVO;

    /* Constructors */
    @Deprecated
    public CartaoModel() {
    }

    // Defini os valores do método da CartaoClientResponse.class em CartaoModel.class
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
    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public Integer getLimite() {
        return limite;
    }

    public PropostaModel getProposta() {
        return proposta;
    }

    public Set <BiometriaModel> getBiometrias() {
        return biometrias;
    }

    public CartaoStatus getStatus() {
        return status;
    }

}