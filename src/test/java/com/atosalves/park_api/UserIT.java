package com.atosalves.park_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.atosalves.park_api.web.dto.user.UserCreateDto;
import com.atosalves.park_api.web.dto.user.UserPasswordDto;
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
        public void create_ValidUsernameAndPassword_ReturnCreatedUserWith201StatusCode() {
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
        public void create_InvalidUsername_ReturnErrorMessageWith422StatusCode() {
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
        public void create_InvalidPassword_ReturnErrorMessageWith422StatusCode() {
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
        public void create_RepeatedUsername_ReturnErrorMessageWith409StatusCode() {
                var statusCode = 409;
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("admin", "123456"))
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
        public void getById_ValidId_ReturnUserByIdWith200StatusCode() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody(UserResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(100L, responseBody.id());
                assertEquals("admin", responseBody.username());
                assertEquals("ADMIN", responseBody.role());

                responseBody = webTestClient
                                .get()
                                .uri("/api/v1/users/101")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody(UserResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(101L, responseBody.id());
                assertEquals("customer", responseBody.username());
                assertEquals("CUSTOMER", responseBody.role());

                responseBody = webTestClient
                                .get()
                                .uri("/api/v1/users/101")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody(UserResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(101L, responseBody.id());
                assertEquals("customer", responseBody.username());
                assertEquals("CUSTOMER", responseBody.role());

        }

        @Test
        public void getById_InvalidId_ReturnErrorMessageWithNotFoundStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/users/0")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .exchange()
                                .expectStatus()
                                .isNotFound()
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(404, responseBody.getStatus());
        }

        @Test
        public void getById_InvalidId_ReturnErrorMessageWithForbbidenStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer", "123456"))
                                .exchange()
                                .expectStatus()
                                .isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(403, responseBody.getStatus());
        }

        @Test
        public void updatePassword_ValidPasswords_ReturnNoContentStatus() {
                webTestClient
                                .patch()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserPasswordDto("123456", "654321", "654321"))
                                .exchange()
                                .expectStatus()
                                .isNoContent();
                webTestClient
                                .patch()
                                .uri("/api/v1/users/101")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer", "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserPasswordDto("123456", "654321", "654321"))
                                .exchange()
                                .expectStatus()
                                .isNoContent();
        }

        @Test
        public void updatePassword_InvalidPasswords_ReturnErrorMessageWithBadRequestStatus() {
                var statusCode = 400;

                var responseBody = webTestClient
                                .patch()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserPasswordDto("wrongCurrentPassword", "654321", "654321"))
                                .exchange()
                                .expectStatus()
                                .isBadRequest()
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(statusCode, responseBody.getStatus());

                responseBody = webTestClient
                                .patch()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserPasswordDto("123456", "654321", "wrongConfirmedPassword"))
                                .exchange()
                                .expectStatus()
                                .isBadRequest()
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(statusCode, responseBody.getStatus());
        }

        @Test
        public void updatePassword_InvalidId_ReturnErrorMessageWithForbbidenStatus() {
                var responseBody = webTestClient
                                .patch()
                                .uri("/api/v1/users/0")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserPasswordDto("123456", "654321", "654321"))
                                .exchange()
                                .expectStatus()
                                .isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(403, responseBody.getStatus());

                responseBody = webTestClient
                                .patch()
                                .uri("/api/v1/users/0")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer", "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserPasswordDto("123456", "654321", "654321"))
                                .exchange()
                                .expectStatus()
                                .isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(403, responseBody.getStatus());
        }

        @Test
        public void updatePassword_InvalidInputs_ReturnErrorMessageWith422StatusCode() {
                var statusCode = 422;

                var responseBody = webTestClient
                                .patch()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserPasswordDto("", "654321", "654321"))
                                .exchange()
                                .expectStatus()
                                .isEqualTo(statusCode)
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(statusCode, responseBody.getStatus());

                responseBody = webTestClient
                                .patch()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserPasswordDto("123456", "654321", ""))
                                .exchange()
                                .expectStatus()
                                .isEqualTo(statusCode)
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(statusCode, responseBody.getStatus());

                responseBody = webTestClient
                                .patch()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserPasswordDto("123456", "", "654321"))
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
        public void getAll_AsAdminRole_ReturnUserListWithOkStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/users")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBodyList(UserResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(3, responseBody.size());
        }

        @Test
        public void getAll_AsCustomerRole_ReturnErrorMessageWithForbbidenStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/users")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer", "123456"))
                                .exchange()
                                .expectStatus()
                                .isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(403, responseBody.getStatus());
        }
}
