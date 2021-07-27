package br.com.zupacademy.giovannimoratto.proposta.cadastra_proposta;

import java.math.BigDecimal;

/**
 * @Author giovanni.moratto
 */

public class BidResponse {

    /* Attributes */
    private final String document;
    private final String email;
    private final String name;
    private final String address;
    private final BigDecimal salary;

    /* Constructors */
    public BidResponse(BidModel bid) {
        this.document = bid.getDocument();
        this.email = bid.getEmail();
        this.name = bid.getName();
        this.address = bid.getAddress();
        this.salary = bid.getSalary();
    }

    /* Getters */
    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}