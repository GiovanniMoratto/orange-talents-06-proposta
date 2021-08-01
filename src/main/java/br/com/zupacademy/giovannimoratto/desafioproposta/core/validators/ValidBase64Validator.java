package br.com.zupacademy.giovannimoratto.desafioproposta.core.validators;

import br.com.zupacademy.giovannimoratto.desafioproposta.core.annotations.ValidBase64;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author giovanni.moratto
 */

public class ValidBase64Validator implements ConstraintValidator <ValidBase64, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
    }

}