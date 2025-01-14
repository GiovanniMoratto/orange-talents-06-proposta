package br.com.zupacademy.giovannimoratto.desafioproposta.proposta;

import br.com.zupacademy.giovannimoratto.desafioproposta.core.annotations.CPForCNPJ;
import br.com.zupacademy.giovannimoratto.desafioproposta.core.annotations.DocumentoUnico;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * @Author giovanni.moratto
 */

public class PropostaRequest {

    /* Attributes */
    @DocumentoUnico
    @CPForCNPJ
    @NotBlank
    private final String documento;
    @Email
    @NotBlank
    private final String email;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String endereco;
    @NotNull
    @Positive
    private final BigDecimal salario;

    /* Constructors */
    public PropostaRequest(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    /* Methods */
    public PropostaModel toModel() {
        return new PropostaModel(documento, email, nome, endereco, salario);
    }

    /* Getters */
    public String getEmail() {
        return email;
    }

}