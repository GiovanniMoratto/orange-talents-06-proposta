package br.com.zupacademy.giovannimoratto.proposta.core.exceptions.handler.validations;

/**
 * @Author giovanni.moratto
 */
public class ExceptionDTO {

    /* Attributes */
    private final String field;
    private final String error;

    /* Constructor */
    public ExceptionDTO(String field, String error) {
        super();
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
