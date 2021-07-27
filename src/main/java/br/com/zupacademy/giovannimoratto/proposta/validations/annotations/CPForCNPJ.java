package br.com.zupacademy.giovannimoratto.proposta.validations.annotations;

import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.OR;

/**
 * @Author giovanni.moratto
 */

@Documented
@CPF
@CNPJ
@ConstraintComposition(OR)
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE})
@Retention(RUNTIME)
public @interface CPForCNPJ {

    String message() default "This field must have a valid format of CPF or CNPJ";

    Class <?>[] groups() default {};

    Class <? extends Payload>[] payload() default {};
}
