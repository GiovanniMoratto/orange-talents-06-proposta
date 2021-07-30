package br.com.zupacademy.giovannimoratto.desafioproposta.core.exceptions.handler;

import java.util.Collection;

/**
 * @Author giovanni.moratto
 */
public class ResponseStatusExceptionDTO {

    /* Attributes */
    private final Collection <String> messages;

    /* Constructor */
    public ResponseStatusExceptionDTO(Collection <String> messages) {
        this.messages = messages;
    }

    /* Getters */
    public Collection <String> getMessages() {
        return messages;
    }

}