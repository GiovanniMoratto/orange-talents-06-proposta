package br.com.zupacademy.giovannimoratto.desafioproposta.criptografia;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @Author giovanni.moratto
 */

@Component
@Converter
public class Encryptor implements AttributeConverter <String, String> {

    TextEncryptor textEncryptor = Encryptors.delux("${criptografia.secret}", "N3gW$prt6av`{OPvHP_#94");

    @Override
    public String convertToDatabaseColumn(String campo) {
        return textEncryptor.encrypt(campo);
    }

    @Override
    public String convertToEntityAttribute(String campo) {
        return textEncryptor.decrypt(campo);
    }

}