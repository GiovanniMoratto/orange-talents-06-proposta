package br.com.zupacademy.giovannimoratto.proposta.cartao;

import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaModel;
import br.com.zupacademy.giovannimoratto.proposta.proposta.PropostaRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */

public class CartaoResponse {

    /* Attributes */
    @JsonProperty("id")
    private final String numero;
    private final LocalDateTime emitidoEm;
    private final String titular;
    private final Integer limite;
    private final String idProposta;

    /* Constructors */
    public CartaoResponse(String numero, LocalDateTime emitidoEm, String titular, Integer limite, String idProposta) {
        this.numero = numero;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.idProposta = idProposta;
    }

    /* Methods */
    public CartaoModel toModel(PropostaRepository propostaRepository) {

        PropostaModel proposta = propostaRepository.findById(Long.parseLong(idProposta)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Essa proposta não existe"));

        return new CartaoModel(numero, emitidoEm, titular, limite, proposta);
    }

    public CartaoModel toModel(PropostaModel proposta) {
        return new CartaoModel(numero, emitidoEm, titular, limite, proposta);
    }

    /* Getters */
    public String getNumero() {
        return numero;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public Integer getLimite() {
        return limite;
    }

    public String getIdProposta() {
        return idProposta;
    }

}