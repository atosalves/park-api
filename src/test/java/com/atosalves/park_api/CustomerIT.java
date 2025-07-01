package com.atosalves.park_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.atosalves.park_api.web.dto.customer.CustomerCreateDto;
import com.atosalves.park_api.web.dto.customer.CustomerResponseDto;
import com.atosalves.park_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/customers/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerIT {

        @Autowired
        WebTestClient webTestClient;

        @Test
        public void create_ValidInputs_ReturnCreatedCustomerWithCreatedStatus() {
                var name = "test";
                var cpf = "85660285031";

                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer3", "123456"))
                                .bodyValue(new CustomerCreateDto(name, cpf))
                                .exchange()
                                .expectStatus()
                                .isCreated()
                                .expectBody(CustomerResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertNotNull(responseBody.id());

                assertEquals(name, responseBody.name());
                assertEquals(cpf, responseBody.cpf());
        }

        @Test
        public void create_RepeatedCpf_ReturnErrorMessageWith409StatusCode() {
                var statusCode = 409;

                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer3", "123456"))
                                .bodyValue(new CustomerCreateDto("test", "22420534000"))
                                .exchange()
                                .expectStatus()
                                .isEqualTo(statusCode)
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(statusCode, responseBody.getStatus());
        }

        @Test
        public void create_InvalidInputs_ReturnErrorMessageWith422StatusCode() {
                var statusCode = 422;

                var name = "";
                var cpf = "85660285031";

                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer3", "123456"))
                                .bodyValue(new CustomerCreateDto(name, cpf))
                                .exchange()
                                .expectStatus()
                                .isEqualTo(statusCode)
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(statusCode, responseBody.getStatus());

                name = "test";
                cpf = "";

                responseBody = webTestClient
                                .post()
                                .uri("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer3", "123456"))
                                .bodyValue(new CustomerCreateDto(name, cpf))
                                .exchange()
                                .expectStatus()
                                .isEqualTo(statusCode)
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(statusCode, responseBody.getStatus());
        }

}
