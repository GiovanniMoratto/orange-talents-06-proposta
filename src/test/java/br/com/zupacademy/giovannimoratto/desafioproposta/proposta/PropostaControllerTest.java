package br.com.zupacademy.giovannimoratto.desafioproposta.proposta;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PropostaControllerTest {

    private final String urlTemplate = "/api/nova-desafioproposta";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @Autowired
    private PropostaRepository repository;

    @BeforeEach
    void setUp() {
        this.repository.deleteAll();
    }

    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY")
    void emptyBodyStatus400() throws Exception {
        mockMvc.perform(post(urlTemplate)
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty OBJECT")
    void emptyObjectStatus400() throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content("{ }")
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1419562", "333.333.333-40", "XX.XXX.XXX/0001-XX"})
    @DisplayName("400 Bad Request - When trying to POST with invalid CPF or CNPJ")
    void documentInvalidStatus400(String documento) throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new PropostaRequest(documento, "email@email.com",
                        "nome", "endereco", valueOf(2000.00))))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail("email@email.com").isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalidEmail.com", "@invalid.com", "@.com", "@invalid"})
    @DisplayName("400 Bad Request - When trying to POST with invalid EMAIL")
    void emailInvalidStatus400(String email) throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new PropostaRequest("574.170.670-34", email,
                        "nome", "endereco", valueOf(2000.00))))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail(email).isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("400 Bad Request - When trying to POST with invalid NAME")
    void nameInvalidStatus400(String nome) throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new PropostaRequest("574.170.670-34", "email@email.com",
                        nome, "endereco", valueOf(2000.00))))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail("email@email.com").isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("400 Bad Request - When trying to POST with invalid ADDRESS")
    void addressInvalidStatus400(String endereco) throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new PropostaRequest("574.170.670-34", "email@email.com",
                        "nome", endereco, valueOf(2000.00))))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail("email@email.com").isEmpty());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-50.75, -5.11, -755.35})
    @DisplayName("400 Bad Request - When trying to POST with negative SALARY")
    void salaryInvalidStatus400(Double salario) throws Exception {
        BigDecimal salarioConvertido = BigDecimal.valueOf(salario);
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new PropostaRequest("574.170.670-34", "email@email.com",
                        "nome", "endereco", salarioConvertido)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail("email@email.com").isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("400 Bad Request - When trying to POST with null value of SALARY")
    void salaryNullStatus400(BigDecimal salario) throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new PropostaRequest("574.170.670-34", "email@email.com",
                        "nome", "endereco", salario)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail("email@email.com").isEmpty());
    }

    @Test
    @DisplayName("201 Create - Succeed and persist the New Bid in the Database")
    void createNewBidStatus201() throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new PropostaRequest("574.170.670-34", "email@email.com",
                        "nome", "endereco", valueOf(2000.00))))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        Assertions.assertTrue(repository.findByEmail("email@email.com").isPresent());
    }

    @Test
    @DisplayName("422 Unprocessable Entity - When trying to POST with duplicate DOCUMENT")
    void documentDuplicateStatus422() throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new PropostaRequest("574.170.670-34", "email@email.com",
                                "nome", "endereco", valueOf(2000.00))))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON));

        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new PropostaRequest("574.170.670-34", "email@email.com",
                        "nome", "endereco", valueOf(2000.00))))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity()).andReturn();
        Assertions.assertEquals(1, repository.countByEmail("email@email.com"));
    }


}
