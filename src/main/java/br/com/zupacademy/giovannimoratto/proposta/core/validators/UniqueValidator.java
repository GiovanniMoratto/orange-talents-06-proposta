package br.com.zupacademy.giovannimoratto.proposta.core.validators;

import br.com.zupacademy.giovannimoratto.proposta.core.annotations.Unique;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author giovanni.moratto
 */

public class UniqueValidator implements ConstraintValidator <Unique, String> {

    @PersistenceContext
    private EntityManager em;

    private String entity;
    private String field;

    @Override
    public void initialize(Unique constraintAnnotation) {
        entity = constraintAnnotation.domainClass().getSimpleName();
        field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            Query query = em.createQuery("FROM " + entity + " WHERE " + field + " = :VALUE");
            return query.setParameter("VALUE", value).getResultList().isEmpty();
        }
        return false;
    }
}
