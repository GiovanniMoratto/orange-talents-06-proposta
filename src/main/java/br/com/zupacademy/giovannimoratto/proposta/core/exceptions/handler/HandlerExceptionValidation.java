package br.com.zupacademy.giovannimoratto.proposta.core.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <ExceptionDTO> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
        Collection <String> messages = new ArrayList <>();
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List <FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String message = String.format("Campo %s %s", fieldError.getField(), fieldError.getDefaultMessage());
            messages.add(message);
        });
        ExceptionDTO error = new ExceptionDTO(messages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity <ExceptionDTO> handleResponseStatusException(ResponseStatusException responseStatusException) {
        Collection <String> messages = new ArrayList <>();
        messages.add(responseStatusException.getReason());
        ExceptionDTO error = new ExceptionDTO(messages);
        return ResponseEntity.status(responseStatusException.getStatus()).body(error);
    }

}