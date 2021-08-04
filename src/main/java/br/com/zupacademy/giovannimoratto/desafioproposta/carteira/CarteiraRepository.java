package br.com.zupacademy.giovannimoratto.desafioproposta.carteira;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface CarteiraRepository extends JpaRepository <CarteiraModel, Long> {

    Optional <CarteiraModel> findByCartaoAndCarteira(CartaoModel cartao, CarteiraType carteira);
}
