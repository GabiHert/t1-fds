package com.fds.siscaa.interfaceAdapters.controller.routes;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fds.siscaa.interfaceAdapters.controller.dto.*;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomMapEditor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.enums.SubscriptionType;
import com.fds.siscaa.domain.utils.CustomLocalDate;
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
                jdbcTemplate.execute("DELETE FROM Promotion");
                jdbcTemplate.execute("DELETE FROM Subscription");
                jdbcTemplate.execute("DELETE FROM Application");
                jdbcTemplate.execute("DELETE FROM Client");

                CustomLocalDate.reset();

        }

        @Test
        void registraPagamentoSemPromocao() {

                String sqlApplication = "INSERT INTO Application (code,name, monthly_fee) VALUES (1, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                String sqlClient = "INSERT INTO Client (code,name, email) VALUES (1, 'Test Client', 'testclient@example.com')";
                jdbcTemplate.execute(sqlClient);

                String sql = "INSERT INTO Subscription (code,start_date, end_date, client_code, application_code) VALUES (1, '2024-01-01', '2024-02-01', 1, 1)";
                jdbcTemplate.execute(sql);

                String sqlPromotion = "INSERT INTO Promotion (code,discount_percentage, extra_days, days_required, application_code) VALUES (1, 20.0, 30, 12, 1)";
                jdbcTemplate.execute(sqlPromotion);

                CreatePaymentDto createPaymentDto = new CreatePaymentDto(
                                1, 1, 2024, 1, 10,
                                Optional.empty());

                ResponseEntity<String> response = testRestTemplate
                                .postForEntity("http://localhost:" + port + "/registrarpagamento", createPaymentDto,
                                                String.class);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(response.getBody()).isNotBlank();

                DocumentContext json = JsonPath.parse(response.getBody());
                assertThat(json.read("$.status", String.class)).isEqualTo(PaymentStatus.PAGAMENTO_OK.toString());
                assertThat(json.read("$.date", String.class)).isEqualTo("2024-03-02");
                assertThat(json.read("$.refundedValue", Float.class)).isEqualTo(0.0f);
        }

        @Test
        void registraPagamentoComPromocao() {

                String sqlApplication = "INSERT INTO Application (code, name, monthly_fee) VALUES (1, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                String sqlClient = "INSERT INTO Client (code, name, email) VALUES (1, 'Test Client', 'testclient@example.com')";
                jdbcTemplate.execute(sqlClient);

                String sql = "INSERT INTO Subscription (code, start_date, end_date, client_code, application_code) VALUES (1, '2024-01-01', '2024-02-01', 1, 1)";
                jdbcTemplate.execute(sql);

                String sqlPromotion = "INSERT INTO Promotion (code, discount_percentage, extra_days, days_required, application_code) VALUES (1, 50.0, 2, 60, 1)";
                jdbcTemplate.execute(sqlPromotion);

                CreatePaymentDto createPaymentDto = new CreatePaymentDto(
                                1, 1, 2024, 1, 20,
                                Optional.empty());

                ResponseEntity<String> response = testRestTemplate
                                .postForEntity("http://localhost:" + port + "/registrarpagamento", createPaymentDto,
                                                String.class);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(response.getBody()).isNotBlank();

                DocumentContext json = JsonPath.parse(response.getBody());
                assertThat(json.read("$.status", String.class)).isEqualTo(PaymentStatus.PAGAMENTO_OK.toString());
                assertThat(json.read("$.date", String.class)).isEqualTo("2024-04-03");
                assertThat(json.read("$.refundedValue", Float.class)).isEqualTo(10.0f);
        }

        @Test
        void registraPagamentoComPromocaoPorParametro() {

                String sqlApplication = "INSERT INTO Application (code, name, monthly_fee) VALUES (1, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                String sqlClient = "INSERT INTO Client (code, name, email) VALUES (1, 'Test Client', 'testclient@example.com')";
                jdbcTemplate.execute(sqlClient);

                String sql = "INSERT INTO Subscription (code, start_date, end_date, client_code, application_code) VALUES (1, '2024-01-01', '2024-02-01', 1, 1)";
                jdbcTemplate.execute(sql);

                String sqlPromotion = "INSERT INTO Promotion (code, discount_percentage, extra_days, days_required, application_code) VALUES (1, 20.0, 2, 60, 1)";
                jdbcTemplate.execute(sqlPromotion);
                sqlPromotion = "INSERT INTO Promotion (code, discount_percentage, extra_days, days_required, application_code) VALUES (2, 50.0, 3, 60, 1)";
                jdbcTemplate.execute(sqlPromotion);
                sqlPromotion = "INSERT INTO Promotion (code, discount_percentage, extra_days, days_required, application_code) VALUES (3, 30.0, 2, 60, 1)";
                jdbcTemplate.execute(sqlPromotion);

                CreatePaymentDto createPaymentDto = new CreatePaymentDto(
                                1, 1, 2024, 1, 20,
                                Optional.of(Long.valueOf(2)));

                ResponseEntity<String> response = testRestTemplate
                                .postForEntity("http://localhost:" + port + "/registrarpagamento", createPaymentDto,
                                                String.class);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(response.getBody()).isNotBlank();

                DocumentContext json = JsonPath.parse(response.getBody());
                assertThat(json.read("$.status", String.class)).isEqualTo(PaymentStatus.PAGAMENTO_OK.toString());
                assertThat(json.read("$.date", String.class)).isEqualTo("2024-04-04");
                assertThat(json.read("$.refundedValue", Float.class)).isEqualTo(10.0f);
        }

        @Test
        void updateCost() {
                String sqlApplication = "INSERT INTO Application (code, name, monthly_fee) VALUES (1, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                UpdateCostDto updateCostDto = new UpdateCostDto(20.00f);

                ResponseEntity<ApplicationDto> response = testRestTemplate
                                .postForEntity("http://localhost:" + port + "/servcad/aplicativos/atualizacusto/1",
                                                updateCostDto, ApplicationDto.class);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();
                assertThat(response.getBody().getCustoMensal()).isEqualTo(20.00f);
        }

        @Test
        void listClients() {
                String sqlClient = "INSERT INTO Client (code, name, email) VALUES (1, 'Test Client', 'testclient@example.com')";
                jdbcTemplate.execute(sqlClient);

                ResponseEntity<List<ClientDto>> response = testRestTemplate
                                .exchange("http://localhost:" + port + "/servcad/clientes", HttpMethod.GET, null,
                                                new ParameterizedTypeReference<List<ClientDto>>() {
                                                });

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotEmpty();

                List<ClientDto> clients = response.getBody();
                assertThat(clients).hasSize(1);

                ClientDto client = clients.get(0);
                assertThat(client.getCodigo()).isEqualTo(1);
                assertThat(client.getNome()).isEqualTo("Test Client");
                assertThat(client.getEmail()).isEqualTo("testclient@example.com");
        }

        @Test
        void listApplication() {
                String sqlApplication = "INSERT INTO Application (code, name, monthly_fee) VALUES (1, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                ResponseEntity<List<ApplicationDto>> response = testRestTemplate
                                .exchange("http://localhost:" + port + "/servcad/aplicativos", HttpMethod.GET, null,
                                                new ParameterizedTypeReference<List<ApplicationDto>>() {
                                                });

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotEmpty();

                List<ApplicationDto> applications = response.getBody();

                ApplicationDto application = applications.get(0);
                assertThat(application.getCodigoAplicativo()).isEqualTo(1);
                assertThat(application.getNome()).isEqualTo("Test Application");
                assertThat(application.getCustoMensal()).isEqualTo(10.00f);
        }

        @Test
        void listSubscriptionsByTypeATIVAS() {
                CustomLocalDate.mock(LocalDate.of(2024, 11, 20));

                String sqlApplication = "INSERT INTO Application (code, name, monthly_fee) VALUES (1, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                String sqlClient = "INSERT INTO Client (code, name, email) VALUES (1, 'Test Client', 'testclient@example.com')";
                jdbcTemplate.execute(sqlClient);

                String sqlSubscription = "INSERT INTO Subscription (code, start_date, end_date, client_code, application_code) VALUES (1, '2024-10-25', '2024-11-21', 1, 1)";
                jdbcTemplate.execute(sqlSubscription);

                ResponseEntity<List<SubscriptionDto>> response = testRestTemplate
                                .exchange("http://localhost:" + port + "/servcad/assinaturas/ATIVAS",
                                                HttpMethod.GET,
                                                null, new ParameterizedTypeReference<List<SubscriptionDto>>() {
                                                });

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotEmpty();

                List<SubscriptionDto> subscriptions = response.getBody();
                assertThat(subscriptions).hasSize(1);

                SubscriptionDto subscription = subscriptions.get(0);
                assertThat(subscription.getCodigoAssinatura()).isEqualTo(1);
                assertThat(subscription.getCodigoCliente()).isEqualTo(1);
                assertThat(subscription.getCodigoAplicativo()).isEqualTo(1);
                assertThat(subscription.getDataDeInicio()).isEqualTo(LocalDate.of(2024, 10, 25));
                assertThat(subscription.getDataDeEncerramento()).isEqualTo(LocalDate.of(2024, 11, 21));
                assertThat(subscription.getStatus()).isEqualTo("ATIVA");
        }

        @Test
        void listSubscriptionsByTypeCANCELADAS() {
                CustomLocalDate.mock(LocalDate.of(2024, 12, 20));

                String sqlApplication = "INSERT INTO Application (code, name, monthly_fee) VALUES (1, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                String sqlClient = "INSERT INTO Client (code, name, email) VALUES (1, 'Test Client', 'testclient@example.com')";
                jdbcTemplate.execute(sqlClient);

                String sqlSubscription = "INSERT INTO Subscription (code, start_date, end_date, client_code, application_code) VALUES (1, '2024-11-10', '2024-12-10', 1, 1)";
                jdbcTemplate.execute(sqlSubscription);

                ResponseEntity<List<SubscriptionDto>> response = testRestTemplate
                                .exchange("http://localhost:" + port + "/servcad/assinaturas/CANCELADAS",
                                                HttpMethod.GET,
                                                null, new ParameterizedTypeReference<List<SubscriptionDto>>() {
                                                });

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotEmpty();

                List<SubscriptionDto> subscriptions = response.getBody();
                assertThat(subscriptions).hasSize(1);

                SubscriptionDto subscription = subscriptions.get(0);
                assertThat(subscription.getCodigoAssinatura()).isEqualTo(1);
                assertThat(subscription.getCodigoCliente()).isEqualTo(1);
                assertThat(subscription.getCodigoAplicativo()).isEqualTo(1);
                assertThat(subscription.getDataDeInicio()).isEqualTo(LocalDate.of(2024, 11, 10));
                assertThat(subscription.getDataDeEncerramento()).isEqualTo(LocalDate.of(2024, 12, 10));
                assertThat(subscription.getStatus()).isEqualTo("CANCELADA");
        }

        @Test
        void listSubscriptionsByTypeTODAS() {

                String sqlApplication = "INSERT INTO Application (code, name, monthly_fee) VALUES (1, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                String sqlClient = "INSERT INTO Client (code, name, email) VALUES (1, 'Test Client', 'testclient@example.com')";
                jdbcTemplate.execute(sqlClient);

                String sqlSubscription = "INSERT INTO Subscription (code, start_date, end_date, client_code, application_code) VALUES (1, '2024-10-25', '2024-11-25', 1, 1),(2, '2022-10-25', '2023-11-25', 1, 1)";
                jdbcTemplate.execute(sqlSubscription);

                ResponseEntity<List<SubscriptionDto>> response = testRestTemplate
                                .exchange("http://localhost:" + port + "/servcad/assinaturas/TODAS", HttpMethod.GET,
                                                null, new ParameterizedTypeReference<List<SubscriptionDto>>() {
                                                });

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotEmpty();

                List<SubscriptionDto> subscriptions = response.getBody();
                assertThat(subscriptions).hasSize(2);

                List<SubscriptionDto> activeSubscriptions = subscriptions.stream()
                                .filter(subscription -> "ATIVA".equals(subscription.getStatus()))
                                .collect(Collectors.toList());
                List<SubscriptionDto> canceledSubscriptions = subscriptions.stream()
                                .filter(subscription -> "CANCELADA"
                                                .equals(subscription.getStatus()))
                                .collect(Collectors.toList());

                assertThat(activeSubscriptions).hasSize(1);
                assertThat(canceledSubscriptions).hasSize(1);

                canceledSubscriptions.forEach(subscription -> {
                        assertThat(subscription.getCodigoAssinatura()).isEqualTo(2);
                        assertThat(subscription.getCodigoCliente()).isEqualTo(1);
                        assertThat(subscription.getCodigoAplicativo()).isEqualTo(1);
                        assertThat(subscription.getDataDeInicio()).isEqualTo(LocalDate.of(2022, 10, 25));
                        assertThat(subscription.getDataDeEncerramento()).isEqualTo(LocalDate.of(2023, 11, 25));
                        assertThat(subscription.getStatus()).isEqualTo("CANCELADA");
                });

                activeSubscriptions.forEach(subscription -> {
                        assertThat(subscription.getCodigoAssinatura()).isEqualTo(1);
                        assertThat(subscription.getCodigoCliente()).isEqualTo(1);
                        assertThat(subscription.getCodigoAplicativo()).isEqualTo(1);
                        assertThat(subscription.getDataDeInicio()).isEqualTo(LocalDate.of(2024, 10, 25));
                        assertThat(subscription.getDataDeEncerramento()).isEqualTo(LocalDate.of(2024, 11, 25));
                        assertThat(subscription.getStatus()).isEqualTo("ATIVA");
                });

        }

        @Test
        void listSubscriptionsByClientCode() {
                CustomLocalDate.mock(LocalDate.of(2024, 11, 23));

                String sqlApplication = "INSERT INTO Application (code, name, monthly_fee) VALUES (1, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                String sqlClient = "INSERT INTO Client (code, name, email) VALUES (1, 'Test Client', 'testclient@example.com')";
                jdbcTemplate.execute(sqlClient);

                String sqlSubscription = "INSERT INTO Subscription (code, start_date, end_date, client_code, application_code) VALUES (1, '2024-10-25', '2024-11-25', 1, 1),(2, '2022-10-25', '2023-11-25', 1, 1)";
                jdbcTemplate.execute(sqlSubscription);

                ResponseEntity<List<SubscriptionDto>> response = testRestTemplate
                                .exchange("http://localhost:" + port + "/servcad/asscli/1", HttpMethod.GET, null,
                                                new ParameterizedTypeReference<List<SubscriptionDto>>() {
                                                });

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotEmpty();
        }

        @Test
        void listSubscriptionsByAppCode() {
                CustomLocalDate.mock(LocalDate.of(2024, 11, 23));

                String sqlApplication = "INSERT INTO Application (code, name, monthly_fee) VALUES (1, 'Test Application', 10.00),(2, 'Test Application', 10.00)";
                jdbcTemplate.execute(sqlApplication);

                String sqlClient = "INSERT INTO Client (code, name, email) VALUES (1, 'Test Client', 'testclient@example.com')";
                jdbcTemplate.execute(sqlClient);

                String sqlSubscription = "INSERT INTO Subscription (code, start_date, end_date, client_code, application_code) VALUES (1, '2024-10-25', '2024-11-25', 1, 1),(2, '2022-10-25', '2023-11-25', 1, 1),(3, '2024-10-25', '2024-11-25', 1, 2)";
                jdbcTemplate.execute(sqlSubscription);

                ResponseEntity<List<SubscriptionDto>> response = testRestTemplate
                                .exchange("http://localhost:" + port + "/servcad/assapp/1", HttpMethod.GET, null,
                                                new ParameterizedTypeReference<List<SubscriptionDto>>() {
                                                });

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotEmpty();
                List<SubscriptionDto> subscriptions = response.getBody();
                assertThat(subscriptions).hasSize(2);

                SubscriptionDto subscription1 = subscriptions.get(0);
                assertThat(subscription1.getCodigoAssinatura()).isEqualTo(1);
                assertThat(subscription1.getCodigoCliente()).isEqualTo(1);
                assertThat(subscription1.getCodigoAplicativo()).isEqualTo(1);
                assertThat(subscription1.getDataDeInicio()).isEqualTo(LocalDate.of(2024, 10, 25));
                assertThat(subscription1.getDataDeEncerramento()).isEqualTo(LocalDate.of(2024, 11, 25));
                assertThat(subscription1.getStatus()).isEqualTo("ATIVA");

                SubscriptionDto subscription2 = subscriptions.get(1);
                assertThat(subscription2.getCodigoAssinatura()).isEqualTo(2);
                assertThat(subscription2.getCodigoCliente()).isEqualTo(1);
                assertThat(subscription2.getCodigoAplicativo()).isEqualTo(1);
                assertThat(subscription2.getDataDeInicio()).isEqualTo(LocalDate.of(2022, 10, 25));
                assertThat(subscription2.getDataDeEncerramento()).isEqualTo(LocalDate.of(2023, 11, 25));
                assertThat(subscription2.getStatus()).isEqualTo("CANCELADA");
        }

        @Test
        void subscriptionIsValid() {
                String sqlSubscription = "INSERT INTO Subscription (code, start_date, end_date, client_code, application_code, status) VALUES (1, '2024-01-01', '2024-02-01', 1, 1)";
                jdbcTemplate.execute(sqlSubscription);

                ResponseEntity<Boolean> response = testRestTemplate
                                .getForEntity("http://localhost:" + port + "/assinvalida/1", Boolean.class);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isTrue();
        }
}
