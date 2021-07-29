package br.com.zupacademy.giovannimoratto.proposta.core.exceptions.handler;

/**
 * @Author giovanni.moratto
 */

public class MethodArgumentNotValidExceptionDTO {

    /* Attributes */
    private final String field;
    private final String error;

    /* Constructor */
    public MethodArgumentNotValidExceptionDTO(String field, String error) {
        this.field = field;
        this.error = error;
    }

    /* Getters */
    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }

}
