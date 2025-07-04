package com.atosalves.park_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.atosalves.park_api.web.dto.parkingSpot.ParkingSpotCreateDto;
import com.atosalves.park_api.web.dto.parkingSpot.ParkingSpotResponseDto;
import com.atosalves.park_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parking_spots/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parking_spots/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingSpotIT {

        @Autowired
        WebTestClient webTestClient;

        @Test
        public void create_ValidInputs_ReturnLocationWithCreatedStatus() {
                webTestClient
                                .post()
                                .uri("/api/v1/parking-spots")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .bodyValue(new ParkingSpotCreateDto("5555", "AVAILABLE"))
                                .exchange()
                                .expectStatus()
                                .isCreated()
                                .expectHeader()
                                .exists(HttpHeaders.LOCATION);
        }

        @Test
        public void create_RepeatedCode_ReturnErrorMessageWith422StatusCode() {
                var statusCode = 409;

                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/parking-spots")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .bodyValue(new ParkingSpotCreateDto("1111", "AVAILABLE"))
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

                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/parking-spots")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .bodyValue(new ParkingSpotCreateDto("555", "AVAILABLE"))
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
                                .uri("/api/v1/parking-spots")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .bodyValue(new ParkingSpotCreateDto("55555", "AVAILABLE"))
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
                                .uri("/api/v1/parking-spots")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .bodyValue(new ParkingSpotCreateDto("5555", "invalid_status"))
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
        public void create_AsCustomer_ReturnErrorMessageWithForbbidenStatus() {
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/parking-spots")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer", "123456"))
                                .bodyValue(new ParkingSpotCreateDto("1111", "AVAILABLE"))
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
        public void getByCode_ValidCode_ReturnParkingSpotWithOkStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parking-spots/1111")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody(ParkingSpotResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals("1111", responseBody.code());
        }

        @Test
        public void getByCode_InvalidCode_ReturnErrorMessageWithNotFoundStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parking-spots/0")
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
        public void getByCode_AsCustomer_ReturnErrorMessageWithForbbidenStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parking-spots/1111")
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
