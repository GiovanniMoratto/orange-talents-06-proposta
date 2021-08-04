package br.com.zupacademy.giovannimoratto.desafioproposta.core.security;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @Author giovanni.moratto
 */

@Converter
public class Encryptor implements AttributeConverter <String, String> {

    TextEncryptor textEncryptor = Encryptors.delux("${criptografia.secret}", "4044ab5fe0a2b89939720333");

    @Override
    public String convertToDatabaseColumn(String campo) {
        return textEncryptor.encrypt(campo);
    }

    @Override
    public String convertToEntityAttribute(String campo) {
        return textEncryptor.decrypt(campo);
    }

}