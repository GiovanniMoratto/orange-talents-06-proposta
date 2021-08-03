package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_bloqueios")
public class BloqueioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String numero;
    @NotBlank
    private String ipCliente;
    @NotBlank
    private String userAgent;
    @CreationTimestamp
    private LocalDateTime bloqueadoEm;
    @NotNull
    @JsonBackReference
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private CartaoModel cartao;
    private boolean ativo = false;

    @Deprecated
    public BloqueioModel() {
    }

    public BloqueioModel(String ipCliente, String userAgent, String numero, CartaoModel cartao) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.numero = numero;
        this.cartao = cartao;
        this.ativo = true;
    }

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
