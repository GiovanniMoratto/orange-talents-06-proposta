package br.com.zupacademy.giovannimoratto.desafioproposta.core.exceptions;

/**
 * @Author giovanni.moratto
 */

public class ErroPadronizadoEnum {

    /* Attributes */
    private final String mensagens;

    /* Constructors */
    public ErroPadronizadoEnum(String mensagens) {
        super();
        this.mensagens = mensagens;
    }

    /* Getters */
    public String getMensagens() {
        return mensagens;
    }

}