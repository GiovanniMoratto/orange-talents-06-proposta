package br.com.zupacademy.giovannimoratto.proposta.add_bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@Repository
public interface BidRepository extends JpaRepository <BidModel, Long> {

    Optional <BidModel> findByEmail(String email);

    int countByEmail(String s);

}
