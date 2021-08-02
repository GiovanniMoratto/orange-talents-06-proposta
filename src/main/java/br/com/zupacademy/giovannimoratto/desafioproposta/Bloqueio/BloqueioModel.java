package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_cartoes_bloqueados")
public class BloqueioModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull
    private CartaoModel cartao;
    @CreationTimestamp
    private LocalDateTime dataBloqueio;
    @NotNull
    private String ipCliente;
    @NotNull
    private String userAgent;

    @Deprecated
    public BloqueioModel() {
    }

    public BloqueioModel(Optional<CartaoModel> cartao, String ipCliente, String userAgent) {
        this.cartao = cartao.get();
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    public Long getId() {
        return id;
    }

    public CartaoModel getCartao() {
        return cartao;
    }

    public LocalDateTime getDataBloqueio() {
        return dataBloqueio;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
