package br.com.zupacademy.giovannimoratto.desafioproposta.core.exceptions;

import java.util.Collection;

/**
 * @Author giovanni.moratto
 */

public class ErroPadronizado {

    private final Collection <String> mensagens;

    public ErroPadronizado(Collection <String> mensagens) {
        super();
        this.mensagens = mensagens;
    }

    public Collection <String> getMensagens() {
        return mensagens;
    }

}