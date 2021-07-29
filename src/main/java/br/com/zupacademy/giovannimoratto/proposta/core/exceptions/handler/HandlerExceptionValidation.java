package br.com.zupacademy.giovannimoratto.proposta.core.exceptions.handler;

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
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author giovanni.moratto
 */

@RestControllerAdvice
public class HandlerExceptionValidation {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List <MethodArgumentNotValidExceptionDTO> handlerError(MethodArgumentNotValidException exception) {

        List <MethodArgumentNotValidExceptionDTO> filterException = new ArrayList <>();
        List <ObjectError> objectErrors = exception.getBindingResult().getGlobalErrors();
        objectErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            MethodArgumentNotValidExceptionDTO error = new MethodArgumentNotValidExceptionDTO(e.getObjectName(),
                    message);
            filterException.add(error);
        });
        List <FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            MethodArgumentNotValidExceptionDTO error = new MethodArgumentNotValidExceptionDTO(e.getField(), message);
            filterException.add(error);
        });
        return filterException;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity <ResponseStatusExceptionDTO> handleResponseStatusException(ResponseStatusException exception) {
        Collection <String> messages = new ArrayList <>();
        messages.add(exception.getReason());
        ResponseStatusExceptionDTO error = new ResponseStatusExceptionDTO(messages);
        return ResponseEntity.status(exception.getStatus()).body(error);
    }

}