package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface BloqueioRepository extends JpaRepository <BloqueioModel, Long> {

    Optional <BloqueioModel> findByNumero(String numero);
}