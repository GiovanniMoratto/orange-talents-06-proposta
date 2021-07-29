package br.com.zupacademy.giovannimoratto.proposta.core.annotations;

import br.com.zupacademy.giovannimoratto.proposta.core.validators.DocumentoUnicoValidator;

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
@Constraint(validatedBy = {DocumentoUnicoValidator.class})
@Target(FIELD)
@Retention(RUNTIME)
public @interface DocumentoUnico {

    String message() default "Este documento já está vinculado a uma proposta!";

    Class <?>[] groups() default {};

    Class <? extends Payload>[] payload() default {};

}
