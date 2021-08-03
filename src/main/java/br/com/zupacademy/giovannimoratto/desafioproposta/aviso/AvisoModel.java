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
public class AvisoModel {

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
    private CartaoModel cartao;

    @Deprecated
    public AvisoModel() {
    }

    public AvisoModel(String destino, LocalDate validoAte, String ipClient, String userAgent, CartaoModel cartao) {
        this.destino = destino;
        this.validoAte = validoAte;
        this.ipClient = ipClient;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

}
