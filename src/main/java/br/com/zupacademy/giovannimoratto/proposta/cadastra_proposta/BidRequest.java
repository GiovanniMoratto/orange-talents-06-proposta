package br.com.zupacademy.giovannimoratto.proposta.cadastra_proposta;

import br.com.zupacademy.giovannimoratto.proposta.validations.annotations.CPForCNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * @Author giovanni.moratto
 */

public class BidRequest {

    /* Attributes */
    @CPForCNPJ
    @NotBlank
    private final String document;
    @Email
    @NotBlank
    private final String email;
    @NotBlank
    private final String name;
    @NotBlank
    private final String address;
    @NotNull
    @Positive
    private final BigDecimal salary;

    /* Constructors */
    public BidRequest(String document, String email, String name, String address, BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    /* Methods */
    public BidModel toModel() {
        return new BidModel(document, email, name, address, salary);
    }

}
