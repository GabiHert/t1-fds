package com.fds.siscaa.interfaceAdapters.controller.routes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.utils.CustomLocalDate;
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

    @Mock
    private CustomLocalDate customLocalDate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM Payment");
        jdbcTemplate.execute("DELETE FROM Subscription");
        jdbcTemplate.execute("DELETE FROM Client");
        jdbcTemplate.execute("DELETE FROM Application");
        jdbcTemplate.execute("DELETE FROM Promotion");
    }

    @Test
    void registraPagamentoSemPromocao() {
        MockedStatic<CustomLocalDate> customLocalDateMock = Mockito.mockStatic(CustomLocalDate.class);
        customLocalDateMock.when(CustomLocalDate::now).thenReturn(LocalDate.of(2024, 1, 1));

        String sqlApplication = "INSERT INTO Application (name, monthly_fee) VALUES ('Test Application', 10.00)";
        jdbcTemplate.execute(sqlApplication);

        String sqlClient = "INSERT INTO Client (name, email) VALUES ('Test Client', 'testclient@example.com')";
        jdbcTemplate.execute(sqlClient);

        String sql = "INSERT INTO Subscription (start_date, end_date, client_code, application_code) VALUES ('2024-01-01', '2024-02-01', 1, 1)";
        jdbcTemplate.execute(sql);

        String sqlPromotion = "INSERT INTO Promotion (discount_percentage, extra_days, months_required, application_code) VALUES (20.0, 30, 12, 1)";
        jdbcTemplate.execute(sqlPromotion);

        CreatePaymentDto createPaymentDto = new CreatePaymentDto(
                1, 1, 2024, 1, 10,
                Optional.empty());

        ResponseEntity<String> response = testRestTemplate
                .postForEntity("http://localhost:" + port + "/registrarpagamento", createPaymentDto, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotBlank();

        DocumentContext json = JsonPath.parse(response.getBody());
        assertThat(json.read("$.status", String.class)).isEqualTo(PaymentStatus.PAGAMENTO_OK.toString());
        assertThat(json.read("$.date", String.class)).isEqualTo("2024-03-02");
        assertThat(json.read("$.refundedValue", Float.class)).isEqualTo(0.0f);
    }

    @Test
    void registraPagamentoComPromocao() {
        MockedStatic<CustomLocalDate> customLocalDateMock = Mockito.mockStatic(CustomLocalDate.class);
        customLocalDateMock.when(CustomLocalDate::now).thenReturn(LocalDate.of(2024, 1, 1));

        String sqlApplication = "INSERT INTO Application (name, monthly_fee) VALUES ('Test Application', 10.00)";
        jdbcTemplate.execute(sqlApplication);

        String sqlClient = "INSERT INTO Client (name, email) VALUES ('Test Client', 'testclient@example.com')";
        jdbcTemplate.execute(sqlClient);

        String sql = "INSERT INTO Subscription (start_date, end_date, client_code, application_code) VALUES ('2024-01-01', '2024-02-01', 1, 1)";
        jdbcTemplate.execute(sql);

        String sqlPromotion = "INSERT INTO Promotion (discount_percentage, extra_days, months_required, application_code) VALUES (50.0, 2, 2, 1)";
        jdbcTemplate.execute(sqlPromotion);

        CreatePaymentDto createPaymentDto = new CreatePaymentDto(
                1, 1, 2024, 1, 20,
                Optional.empty());

        ResponseEntity<String> response = testRestTemplate
                .postForEntity("http://localhost:" + port + "/registrarpagamento", createPaymentDto, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotBlank();

        DocumentContext json = JsonPath.parse(response.getBody());
        assertThat(json.read("$.status", String.class)).isEqualTo(PaymentStatus.PAGAMENTO_OK.toString());
        assertThat(json.read("$.date", String.class)).isEqualTo("2024-04-03");
        assertThat(json.read("$.refundedValue", Float.class)).isEqualTo(10.0f);
    }

    @Test
    void registraPagamentoComPromocaoPorParametro() {
        MockedStatic<CustomLocalDate> customLocalDateMock = Mockito.mockStatic(CustomLocalDate.class);
        customLocalDateMock.when(CustomLocalDate::now).thenReturn(LocalDate.of(2024, 1, 1));

        String sqlApplication = "INSERT INTO Application (name, monthly_fee) VALUES ('Test Application', 10.00)";
        jdbcTemplate.execute(sqlApplication);

        String sqlClient = "INSERT INTO Client (name, email) VALUES ('Test Client', 'testclient@example.com')";
        jdbcTemplate.execute(sqlClient);

        String sql = "INSERT INTO Subscription (start_date, end_date, client_code, application_code) VALUES ('2024-01-01', '2024-02-01', 1, 1)";
        jdbcTemplate.execute(sql);

        String sqlPromotion = "INSERT INTO Promotion (discount_percentage, extra_days, months_required, application_code) VALUES (20.0, 2, 2, 1)";
        jdbcTemplate.execute(sqlPromotion);
        sqlPromotion = "INSERT INTO Promotion (discount_percentage, extra_days, months_required, application_code) VALUES (50.0, 3, 2, 1)";
        jdbcTemplate.execute(sqlPromotion);
        sqlPromotion = "INSERT INTO Promotion (discount_percentage, extra_days, months_required, application_code) VALUES (30.0, 2, 2, 1)";
        jdbcTemplate.execute(sqlPromotion);

        CreatePaymentDto createPaymentDto = new CreatePaymentDto(
                1, 1, 2024, 1, 20,
                Optional.of(Long.valueOf(2)));

        ResponseEntity<String> response = testRestTemplate
                .postForEntity("http://localhost:" + port + "/registrarpagamento", createPaymentDto, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotBlank();

        DocumentContext json = JsonPath.parse(response.getBody());
        assertThat(json.read("$.status", String.class)).isEqualTo(PaymentStatus.PAGAMENTO_OK.toString());
        assertThat(json.read("$.date", String.class)).isEqualTo("2024-04-04");
        assertThat(json.read("$.refundedValue", Float.class)).isEqualTo(10.0f);
    }
}
