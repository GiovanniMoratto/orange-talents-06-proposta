package br.com.zupacademy.giovannimoratto.proposta.core.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @Author giovanni.moratto
 */

public class ApiErrorException extends RuntimeException {

    private final HttpStatus status;
    private final String field;
    private final String message;

    public ApiErrorException(HttpStatus status, String field, String message) {
        this.status = status;
        this.field = field;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
