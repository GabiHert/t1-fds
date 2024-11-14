package com.fds.siscaa.interfaceAdapters.controller.routes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fds.siscaa.interfaceAdapters.controller.dto.CreatePaymentDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RoutesTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM Payment");
        jdbcTemplate.execute("DELETE FROM Subscription");
        jdbcTemplate.execute("DELETE FROM Client");
        jdbcTemplate.execute("DELETE FROM Application");
        jdbcTemplate.execute("DELETE FROM Promotion");
    }

    @Test
    void registraPagamentoComPromocao() {

        String sqlApplication = "INSERT INTO Application (name, monthly_fee) VALUES ('Test Application', 10.00)";
        jdbcTemplate.execute(sqlApplication);

        String sqlClient = "INSERT INTO Client (name, email) VALUES ('Test Client', 'testclient@example.com')";
        jdbcTemplate.execute(sqlClient);

        String sql = "INSERT INTO Subscription (start_date, end_date, client_code, application_code) VALUES (CURRENT_DATE, DATEADD(YEAR, 1, CURRENT_DATE), 1, 1)";
        jdbcTemplate.execute(sql);

        String sqlPromotion = "INSERT INTO Promotion (discount_percentage, extra_days, months_required, application_code) VALUES (20.0, 30, 12, 1)";
        jdbcTemplate.execute(sqlPromotion);

        CreatePaymentDto createPaymentDto = new CreatePaymentDto(
                1, 1, 2024, 1, 1,
                Optional.of(Integer.valueOf(1)));

        ResponseEntity<String> response = testRestTemplate
                .postForEntity("http://localhost:" + port + "/registrarpagamento", createPaymentDto, String.class);
                
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotBlank();

        // DocumentContext json = JsonPath.parse(response.getBody());
        // String isbn = json.read("$.isbn");
        // assertThat(isbn).isEqualTo("456");
        // Number price = json.read("$.price");
        // assertThat(price).isEqualTo(1);
        // Number amount = json.read("$.amount");
        // assertThat(amount).isEqualTo(1);


    }
}
