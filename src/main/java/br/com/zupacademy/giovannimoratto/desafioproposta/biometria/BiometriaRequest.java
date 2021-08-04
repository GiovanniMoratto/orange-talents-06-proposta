package br.com.zupacademy.giovannimoratto.desafioproposta.biometria;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.core.annotations.ValidBase64;

import javax.validation.constraints.NotBlank;

/**
 * @Author giovanni.moratto
 */

public class BiometriaRequest {

    /* Attributes */
    @NotBlank
    @ValidBase64
    private String fingerprint;

    /* Constructors */
    @Deprecated
    public BiometriaRequest() {
    }

    public BiometriaRequest(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    /* Methods */
    public BiometriaModel toModel(CartaoModel cartao) {
        return new BiometriaModel(fingerprint, cartao);
    }

    /* Getters */
    public String getFingerprint() {
        return fingerprint;
    }

}