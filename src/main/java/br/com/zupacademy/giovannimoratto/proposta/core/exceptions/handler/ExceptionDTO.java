package br.com.zupacademy.giovannimoratto.proposta.core.exceptions.handler;

import java.util.Collection;

/**
 * @Author giovanni.moratto
 */

public class ExceptionDTO {

    private Collection <String> messages;

    public ExceptionDTO(Collection <String> messages) {
        super();
        this.messages = messages;
    }

    public Collection <String> getMessages() {
        return messages;
    }

    public void setMessages(Collection <String> messages) {
        this.messages = messages;
    }

}
