package br.com.zupacademy.giovannimoratto.desafioproposta.carteira;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;
import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoRepository;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.CartoesFeignClient;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.request.SolicitacaoInclusaoCarteira;
import br.com.zupacademy.giovannimoratto.desafioproposta.feign.response.ResultadoCarteira;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * @Author giovanni.moratto
 */

@RestController
@RequestMapping("/api")
public class CarteiraController {

    private final Logger logger = LoggerFactory.getLogger(CarteiraController.class);
    private final CartaoRepository cartaoRepository;
    private final CarteiraRepository carteiraRepository;
    private final CartoesFeignClient feignClient;
    private final Tracer tracer;

    @Autowired
    public CarteiraController(CartaoRepository cartaoRepository,
                              CarteiraRepository carteiraRepository,
                              CartoesFeignClient feignClient, Tracer tracer) {
        this.cartaoRepository = cartaoRepository;
        this.carteiraRepository = carteiraRepository;
        this.feignClient = feignClient;
        this.tracer = tracer;
    }

    @PostMapping("/cartoes/carteira-digital/{id}")
    public ResponseEntity <?> associarCartaoEmCarteiraDigital(@PathVariable("id") Long id,
                                                              @RequestBody @Valid CarteiraRequest request,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("user.email", request.getEmail());
        CartaoModel cartao = verificaSeCartaoExiste(id);
        logger.info("Cartão encontrado");
        logger.info("Requisição de carteira digital recebida e validada");
        verificaSeJaFoiAssociado(cartao, request);
        cadastrarCarteira(cartao, request);

        CarteiraModel novaCarteira = request.toModel(cartao);
        logger.info("Requisição de carteira digital convertida em classe de domínio");

        carteiraRepository.save(novaCarteira);
        logger.info("Carteira digital persistida no banco de dados");

        URI uri = uriComponentsBuilder.path("/carteira/{id}").build(novaCarteira.getId());

        return ResponseEntity.created(uri).build();
    }

    private CartaoModel verificaSeCartaoExiste(Long id) {
        logger.info("Buscando cartão por ID...");

        return cartaoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Este cartão não existe"));
    }

    private void verificaSeJaFoiAssociado(CartaoModel cartao, CarteiraRequest request) {
        logger.info("Verificando se a carteira já foi associada ao cartão...");

        Optional <CarteiraModel> carteira = carteiraRepository
                .findByCartaoAndCarteira(cartao, request.getCarteira());
        if (carteira.isPresent()) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Carteira ja associada");
        }
    }

    private void cadastrarCarteira(CartaoModel cartao, CarteiraRequest request) {
        logger.info("Enviando solicitação de cadastro de carteira digital ao cliente...");

        try {
            ResultadoCarteira resultado = feignClient.cadastraCarteiraDigital(
                    cartao.getNumero(),
                    new SolicitacaoInclusaoCarteira(request));
            logger.info("Resultado da solicitação: {}", resultado.getResultado());
        } catch (FeignException e) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Falha no sistema");
        }
    }
}
