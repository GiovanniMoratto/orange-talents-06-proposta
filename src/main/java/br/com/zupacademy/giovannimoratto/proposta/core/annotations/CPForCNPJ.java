package br.com.zupacademy.giovannimoratto.proposta.core.annotations;

import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.OR;

/**
 * @Author giovanni.moratto
 */

@Documented
@CPF
@CNPJ
@ConstraintComposition(OR)
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface CPForCNPJ {

    String message() default "Este campo deve ter um formato válido de CPF ou CNPJ";

    Class <?>[] groups() default {};

    Class <? extends Payload>[] payload() default {};
}
