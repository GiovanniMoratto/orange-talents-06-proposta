package br.com.zupacademy.giovannimoratto.desafioproposta.core.exceptions;

import java.util.Collection;

/**
 * @Author giovanni.moratto
 */

public class ErroPadronizado {

    /* Attributes */
    private final Collection <String> mensagens;

    /* Constructors */
    public ErroPadronizado(Collection <String> mensagens) {
        super();
        this.mensagens = mensagens;
    }

    /* Getters */
    public Collection <String> getMensagens() {
        return mensagens;
    }

}