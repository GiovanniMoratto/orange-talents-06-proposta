package br.com.zupacademy.giovannimoratto.proposta.validations.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author giovanni.moratto
 */

@Documented
@Constraint(validatedBy = {UniqueValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE})
@Retention(RUNTIME)
public @interface Unique {

    // Require Information
    Class <?> domainClass();

    // Require Information
    String fieldName();

    // default
    String message() default "This value already exists!";

    // default
    Class <?>[] groups() default {};

    // default
    Class <? extends Payload>[] payload() default {};

}
