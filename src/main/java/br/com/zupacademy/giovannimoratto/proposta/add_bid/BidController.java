package br.com.zupacademy.giovannimoratto.proposta.add_bid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class BidController {

    @Autowired
    private BidRepository repository;

    @PostMapping("/nova-proposta")
    @Transactional
    public ResponseEntity <BidResponse> cadastraProposta(@RequestBody @Valid BidRequest request,
                                                         UriComponentsBuilder uriBuilder) {
        BidModel newBid = request.toModel();
        repository.save(newBid);
        URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(newBid.getId()).toUri();
        return ResponseEntity.created(uri).body(new BidResponse(newBid));
    }
}
