package br.com.zupacademy.giovannimoratto.desafioproposta.biometria;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_biometrias")
public class BiometriaModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fingerprint;
    @NotNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(nullable = false)
    private CartaoModel cartao;
    @CreationTimestamp
    private LocalDateTime associadaEm;

    /* Constructors */
    @Deprecated
    public BiometriaModel() {
    }

    public BiometriaModel(String fingerprint, CartaoModel cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public CartaoModel getCartao() {
        return cartao;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }

}