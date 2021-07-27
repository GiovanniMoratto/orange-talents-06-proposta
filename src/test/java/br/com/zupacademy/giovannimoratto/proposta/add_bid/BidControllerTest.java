package br.com.zupacademy.giovannimoratto.proposta.add_bid;

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
class BidControllerTest {

    private final String urlTemplate = "/api/nova-proposta";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @Autowired
    private BidRepository repository;

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
    void documentInvalidStatus400(String document) throws Exception {
        String email = "email@email.com";
        String name = "nome";
        String address = "endereco";
        BigDecimal salary = valueOf(2000.00);
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new BidRequest(document, email, name, address, salary)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail(email).isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalidEmail.com", "@invalid.com", "@.com", "@invalid"})
    @DisplayName("400 Bad Request - When trying to POST with invalid EMAIL")
    void emailInvalidStatus400(String email) throws Exception {
        String document = "574.170.670-34";
        String name = "nome";
        String address = "endereco";
        BigDecimal salary = valueOf(2000.00);
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new BidRequest(document, email, name, address, salary)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail(email).isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("400 Bad Request - When trying to POST with invalid NAME")
    void nameInvalidStatus400(String name) throws Exception {
        String document = "574.170.670-34";
        String email = "email@email.com";
        String address = "endereco";
        BigDecimal salary = valueOf(2000.00);
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new BidRequest(document, email, name, address, salary)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail(email).isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("400 Bad Request - When trying to POST with invalid ADDRESS")
    void addressInvalidStatus400(String address) throws Exception {
        String document = "574.170.670-34";
        String email = "email@email.com";
        String name = "nome";
        BigDecimal salary = valueOf(2000.00);
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new BidRequest(document, email, name, address, salary)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail(email).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-50.75, -5.11, -755.35})
    @DisplayName("400 Bad Request - When trying to POST with negative SALARY")
    void salaryInvalidStatus400(Double salary) throws Exception {
        String document = "574.170.670-34";
        String email = "email@email.com";
        String name = "nome";
        String address = "endereco";
        BigDecimal salaryConverted = BigDecimal.valueOf(salary);
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new BidRequest(document, email, name, address, salaryConverted)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail(email).isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("400 Bad Request - When trying to POST with null value of SALARY")
    void salaryNullStatus400(BigDecimal salary) throws Exception {
        String document = "574.170.670-34";
        String email = "email@email.com";
        String name = "nome";
        String address = "endereco";
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new BidRequest(document, email, name, address, salary)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(repository.findByEmail(email).isEmpty());
    }

    @Test
    @DisplayName("201 Create - Succeed and persist the New Bid in the Database")
    void createNewBidStatus201() throws Exception {
        String document = "574.170.670-34";
        String email = "email@email.com";
        String name = "nome";
        String address = "endereco";
        BigDecimal salary = valueOf(2000.00);
        mockMvc.perform(post(urlTemplate)
                .content(gson.toJson(new BidRequest(document, email, name, address, salary)))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        Assertions.assertTrue(repository.findByEmail(email).isPresent());
    }


}
