package br.com.zupacademy.giovannimoratto.desafioproposta.feign.request;

import br.com.zupacademy.giovannimoratto.desafioproposta.carteira.CarteiraRequest;

/**
 * @Author giovanni.moratto
 */

public class SolicitacaoInclusaoCarteira {

    /* Attributes */
    private final String email;
    private final String carteira;

    /* Constructors */
    public SolicitacaoInclusaoCarteira(CarteiraRequest request) {
        this.email = request.getEmail();
        this.carteira = request.getCarteira().toString();
    }

    /* Getters */
    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
