package br.com.zupacademy.giovannimoratto.desafioproposta.bloqueio;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_bloqueios")
public class BloqueioModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String numero;
    @NotBlank
    @Column(nullable = false)
    private String ipCliente;
    @NotBlank
    @Column(nullable = false)
    private String userAgent;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime bloqueadoEm;
    @NotNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    private CartaoModel cartao;
    private boolean ativo = false;

    /* Constructors */
    @Deprecated
    public BloqueioModel() {
    }

    public BloqueioModel(HttpServletRequest request, String userAgent, CartaoModel cartao) {
        this.ipCliente = request.getRemoteAddr();
        this.userAgent = userAgent;
        this.numero = cartao.getNumero();
        this.cartao = cartao;
        this.ativo = true;
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }

    public CartaoModel getCartao() {
        return cartao;
    }

    public boolean isAtivo() {
        return ativo;
    }

}