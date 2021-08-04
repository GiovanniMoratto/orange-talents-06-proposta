package br.com.zupacademy.giovannimoratto.desafioproposta.carteira;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author giovanni.moratto
 */

public class CarteiraRequest {

    /* Attributes */
    @NotBlank
    @Email
    private final String email;
    @NotNull
    private final CarteiraType carteira;

    /* Constructors */
    public CarteiraRequest(String email, CarteiraType carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    /* Methods */
    public CarteiraModel toModel(CartaoModel cartao) {
        return new CarteiraModel(email, carteira, cartao);
    }

    /* Getters */
    public String getEmail() {
        return email;
    }

    public CarteiraType getCarteira() {
        return carteira;
    }

}