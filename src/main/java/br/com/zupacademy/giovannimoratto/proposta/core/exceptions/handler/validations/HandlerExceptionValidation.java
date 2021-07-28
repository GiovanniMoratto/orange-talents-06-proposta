package br.com.zupacademy.giovannimoratto.proposta.core.exceptions.handler.validations;

import br.com.zupacademy.giovannimoratto.proposta.core.exceptions.ApiErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author giovanni.moratto
 */

@RestControllerAdvice
public class HandlerExceptionValidation {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List <ExceptionDTO> handlerError(MethodArgumentNotValidException exception) {

        List <ExceptionDTO> filterException = new ArrayList <>();
        List <ObjectError> objectErrors = exception.getBindingResult().getGlobalErrors();
        objectErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ExceptionDTO error = new ExceptionDTO(e.getObjectName(), message);
            filterException.add(error);
        });
        List <FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ExceptionDTO error = new ExceptionDTO(e.getField(), message);
            filterException.add(error);
        });
        return filterException;
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity <?> handleApiErrorResponseException(ApiErrorException exception) {
        return ResponseEntity.status(exception.getStatus()).body(Map.of("field",
                exception.getField(), "error", exception.getMessage()));
    }

}