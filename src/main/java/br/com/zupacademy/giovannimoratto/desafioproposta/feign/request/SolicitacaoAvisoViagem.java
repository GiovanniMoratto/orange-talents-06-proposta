package br.com.zupacademy.giovannimoratto.desafioproposta.feign.request;

import br.com.zupacademy.giovannimoratto.desafioproposta.aviso.AvisoViagemRequest;

import java.time.LocalDate;

/**
 * @Author giovanni.moratto
 */

public class SolicitacaoAvisoViagem {

    /* Attributes */
    private final String destino;
    private final LocalDate validoAte;

    /* Constructors */
    public SolicitacaoAvisoViagem(AvisoViagemRequest request) {
        this.destino = request.getDestino();
        this.validoAte = request.getValidoAte();
    }

    /* Getters */
    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

}