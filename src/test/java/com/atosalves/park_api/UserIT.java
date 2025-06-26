package com.atosalves.park_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.atosalves.park_api.web.dto.user.UserCreateDto;
import com.atosalves.park_api.web.dto.user.UserResponseDto;
import com.atosalves.park_api.web.exception.ErrorMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

        @Autowired
        WebTestClient webTestClient;

        @Test
        public void createUser_ValidUsernameAndPassword_ReturnCreatedUserWith201StatusCode() {
                var username = "test";

                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto(username, "123456"))
                                .exchange()
                                .expectStatus()
                                .isCreated()
                                .expectBody(UserResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertNotNull(responseBody.id());

                assertNotNull(responseBody.username());
                assertEquals(username, responseBody.username());

                assertNotNull(responseBody.role());
                assertEquals("CUSTOMER", responseBody.role());
        }

        @Test
        public void createUser_InvalidUsername_ReturnErrorMessageWith422StatusCode() {
                var statusCode = 422;
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("", "123456"))
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
        public void createUser_InvalidPassword_ReturnErrorMessageWith422StatusCode() {
                var statusCode = 422;
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("test", ""))
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
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("test", "12345"))
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
        public void createUser_RepeatedUsername_ReturnErrorMessageWithBadRequestCode() {
                var statusCode = 409;
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("test1", "123456"))
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
