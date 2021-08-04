package br.com.zupacademy.giovannimoratto.desafioproposta.carteira;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_carteiras")
public class CarteiraModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    @Enumerated(EnumType.STRING)
    private CarteiraType carteira;
    @JsonBackReference
    @ManyToOne
    private CartaoModel cartao;
    private String numero;

    /* Constructors */
    @Deprecated
    public CarteiraModel() {
    }

    // Defini os valores do método da CarteiraRequest.class em CarteiraModel.class
    public CarteiraModel(String email, CarteiraType carteira, CartaoModel cartao) {
        this.email = email;
        this.carteira = carteira;
        this.cartao = cartao;
        this.numero = cartao.getNumero();
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public CarteiraType getCarteira() {
        return carteira;
    }

    public CartaoModel getCartao() {
        return cartao;
    }

    public String getNumero() {
        return numero;
    }
}
