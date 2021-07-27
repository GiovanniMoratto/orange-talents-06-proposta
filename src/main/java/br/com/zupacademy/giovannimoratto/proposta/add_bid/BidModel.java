package br.com.zupacademy.giovannimoratto.proposta.add_bid;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_propostas")
public class BidModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "documento", nullable = false)
    private String document;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "nome", nullable = false)
    private String name;
    @Column(name = "endereco", nullable = false)
    private String address;
    @Column(name = "salario", nullable = false)
    private BigDecimal salary;

    /* Constructors */
    @Deprecated
    public BidModel() {
    }

    // Set BidRequest.class method values in BidModel.class
    public BidModel(String document, String email, String name, String address, BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    /* Getters */
    public Long getId() {
        return id;
    }

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