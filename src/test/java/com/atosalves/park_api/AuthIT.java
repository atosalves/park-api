package com.atosalves.park_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.atosalves.park_api.web.dto.TokenDto;
import com.atosalves.park_api.web.dto.user.UserLoginDto;
import com.atosalves.park_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthIT {

        @Autowired
        WebTestClient webTestClient;

        @Test
        public void login_ValidCredentials_ReturnTokenWithOkStatus() {
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/auth")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserLoginDto("admin", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody(TokenDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertNotNull(responseBody.token());
        }

        @Test
        public void login_InvalidCredentials_ReturnErrorMessageWith400StatusCode() {
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/auth")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserLoginDto("invalid_username", "123456"))
                                .exchange()
                                .expectStatus()
                                .isBadRequest()
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(400, responseBody.getStatus());

                responseBody = webTestClient
                                .post()
                                .uri("/api/v1/auth")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserLoginDto("admin", "invalid_password"))
                                .exchange()
                                .expectStatus()
                                .isBadRequest()
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(400, responseBody.getStatus());
        }

        @Test
        public void login_InvalidInputs_ReturnErrorMessageWith422StatusCode() {
                var statusCode = 422;
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/auth")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserLoginDto("", "123456"))
                                .exchange()
                                .expectStatus()
                                .isEqualTo(statusCode)
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(statusCode, responseBody.getStatus());

                responseBody = webTestClient
                                .post()
                                .uri("/api/v1/auth")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserLoginDto("admin", ""))
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
