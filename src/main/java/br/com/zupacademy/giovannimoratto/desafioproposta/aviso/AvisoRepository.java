package br.com.zupacademy.giovannimoratto.desafioproposta.aviso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface AvisoRepository extends JpaRepository <AvisoModel, Long> {
}
