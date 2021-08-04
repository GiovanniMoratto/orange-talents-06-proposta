package br.com.zupacademy.giovannimoratto.desafioproposta.aviso;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_avisos_de_viagem")
public class AvisoViagemModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String destino;
    @Column(nullable = false)
    private LocalDate validoAte;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataCriacao;
    @Column(nullable = false)
    private String ipClient;
    @Column(nullable = false)
    private String userAgent;
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(nullable = false)
    private CartaoModel cartao;

    /* Constructors */
    @Deprecated
    public AvisoViagemModel() {
    }

    // Defini os valores do método da AvisoViagemRequest.class em AvisoViagemModel.class
    public AvisoViagemModel(String destino, LocalDate validoAte, String ipClient, String userAgent,
                            CartaoModel cartao) {
        this.destino = destino;
        this.validoAte = validoAte;
        this.ipClient = ipClient;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getIpClient() {
        return ipClient;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public CartaoModel getCartao() {
        return cartao;
    }
}
