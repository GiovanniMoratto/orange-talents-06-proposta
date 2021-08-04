package br.com.zupacademy.giovannimoratto.desafioproposta.proposta;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface PropostaRepository extends JpaRepository <PropostaModel, Long> {

    Optional <PropostaModel> findByEmail(String email);

    int countByEmail(String email);

    List <PropostaModel> findByStatusAndCartao(PropostaStatus status, CartaoModel cartao);
    
}
