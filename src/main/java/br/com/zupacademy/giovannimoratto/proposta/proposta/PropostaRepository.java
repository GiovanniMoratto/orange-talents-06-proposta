package br.com.zupacademy.giovannimoratto.proposta.proposta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface PropostaRepository extends JpaRepository <PropostaModel, Long> {

    Optional <PropostaModel> findByEmail(String email);

    int countByEmail(String email);

}
