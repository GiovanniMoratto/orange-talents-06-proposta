package br.com.zupacademy.giovannimoratto.desafioproposta.biometria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface BiometriaRepository extends JpaRepository <BiometriaModel, Long> {
}
