package br.com.zupacademy.giovannimoratto.proposta.core.validators;

import br.com.zupacademy.giovannimoratto.proposta.core.annotations.DocumentoUnico;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * @Author giovanni.moratto
 */

public class DocumentoUnicoValidator implements ConstraintValidator <DocumentoUnico, String> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(DocumentoUnico constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            Query query = em.createQuery("FROM PropostaModel o WHERE o.documento = :VALUE");
            boolean result = query.setParameter("VALUE", value).getResultList().isEmpty();
            if (!result) {
                throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Documento já vinculado a uma proposta.");
            }
        }
        return true;
    }
}
