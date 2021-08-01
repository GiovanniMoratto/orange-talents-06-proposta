package br.com.zupacademy.giovannimoratto.desafioproposta.core.annotations;

import br.com.zupacademy.giovannimoratto.desafioproposta.core.validators.ValidBase64Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author giovanni.moratto
 */

@Documented
@Constraint(validatedBy = {ValidBase64Validator.class})
@Target(FIELD)
@Retention(RUNTIME)
public @interface ValidBase64 {

    String message() default "Formato inválido! Campo deve possuir formato Base64.";

    Class <?>[] groups() default {};

    Class <? extends Payload>[] payload() default {};

}
