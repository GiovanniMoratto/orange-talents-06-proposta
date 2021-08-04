package br.com.zupacademy.giovannimoratto.desafioproposta.core.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @Author giovanni.moratto
 */

@RestControllerAdvice
public class HandlerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <ErroPadronizado> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
        Collection <String> mensagens = new ArrayList <>();
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List <FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String message = String.format("Campo %s %s", fieldError.getField(), messageSource.getMessage(
                    fieldError,
                    LocaleContextHolder.getLocale()));
            mensagens.add(message);
        });

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity <ErroPadronizado> handleResponseStatusException(ResponseStatusException responseStatusException) {
        Collection <String> mensagens = new ArrayList <>();
        mensagens.add(responseStatusException.getReason());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(responseStatusException.getStatus()).body(erroPadronizado);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity <ErroPadronizadoEnum> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        String genericMessage = "Unacceptable JSON " + exception.getMessage();
        String mensagens = genericMessage;
        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ifx = (InvalidFormatException) exception.getCause();
            if (ifx.getTargetType().isEnum()) {
                mensagens = String.format("Valor: '%s' inválido para o campo: '%s'. O valor deve ser um dos " +
                                          "seguintes: %s.",
                        ifx.getValue(), ifx.getPath().get(ifx.getPath().size() - 1).getFieldName(),
                        Arrays.toString(ifx.getTargetType().getEnumConstants()));
            }
        }
        ErroPadronizadoEnum erroPadronizadoEnum = new ErroPadronizadoEnum(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizadoEnum);
    }

}
