package br.com.zupacademy.giovannimoratto.proposta.core.validators;

import br.com.zupacademy.giovannimoratto.proposta.core.annotations.DocumentExists;
import br.com.zupacademy.giovannimoratto.proposta.core.exceptions.ApiErrorException;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author giovanni.moratto
 */

public class DocumentExistsValidator implements ConstraintValidator <DocumentExists, String> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(DocumentExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            Query query = em.createQuery("FROM BidModel o WHERE o.document = :VALUE");
            boolean result = query.setParameter("VALUE", value).getResultList().isEmpty();
            if (!result) {
                throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "document", "Documento já vinculado a " +
                                                                                         "uma prosposta.");
            }
        }
        return true;
    }
}
