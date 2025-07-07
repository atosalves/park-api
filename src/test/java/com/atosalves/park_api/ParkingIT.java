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

import com.atosalves.park_api.web.dto.PageableDto;
import com.atosalves.park_api.web.dto.parking.ParkingCreateDto;
import com.atosalves.park_api.web.dto.parking.ParkingResponseDto;
import com.atosalves.park_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers_parking_spots/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/customers_parking_spots/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingIT {

        @Autowired
        WebTestClient webTestClient;

        @Test
        public void checkIn_ValidInputs_ReturnCustomerParkingSpotWithCreatedStatusAndLocation() {
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/parkings/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .bodyValue(new ParkingCreateDto("77115870071", "ZZZ-0000", "Hyundai", "HB20", "Preto"))
                                .exchange()
                                .expectStatus()
                                .isCreated()
                                .expectHeader()
                                .exists(HttpHeaders.LOCATION)
                                .expectBody(ParkingResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertNotNull(responseBody.parkingSpotCode());
                assertNotNull(responseBody.receipt());

                assertEquals("77115870071", responseBody.customerCpf());
                assertEquals("HB20", responseBody.model());
        }

        @Test
        public void checkIn_AsCustomer_ReturnErrorMessageWithForbbidenStatus() {
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/parkings/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer", "123456"))
                                .bodyValue(new ParkingCreateDto("77115870071", "ZZZ-0000", "Hyundai", "HB20", "Preto"))
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
        public void checkIn_InvalidInputs_ReturnErrorMessageWithForbbidenStatus() {
                var statusCode = 422;

                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/parkings/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .bodyValue(new ParkingCreateDto("", "", "", "", ""))
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
        public void checkIn_InvalidCpf_ReturnErrorMessageWithNotFoundStatus() {
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/parkings/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .bodyValue(new ParkingCreateDto("95866960060", "ZZZ-0000", "Hyundai", "HB20", "Preto"))
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
        @Sql(scripts = "/sql/customers_parking_spots/occupied_parking_spot/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = "/sql/customers_parking_spots/occupied_parking_spot/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        public void checkIn_OccupiedParkingSpot_ReturnErrorMessageWithNotFoundStatus() {
                var responseBody = webTestClient
                                .post()
                                .uri("/api/v1/parkings/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .bodyValue(new ParkingCreateDto("77115870071", "ZZZ-0000", "Hyundai", "HB20", "Preto"))
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
        public void getByReceipt_ValidReceipt_ReturnCustomerParkingSpotWithOkStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parkings/check-in/{receipt}", "AAA-1111-20250102-000000")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody(ParkingResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertNotNull(responseBody.parkingSpotCode());
                assertNotNull(responseBody.receipt());

                assertEquals("77115870071", responseBody.customerCpf());
                assertEquals("Celta", responseBody.model());

                responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parkings/check-in/{receipt}", "AAA-1111-20250102-000000")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody(ParkingResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertNotNull(responseBody.parkingSpotCode());
                assertNotNull(responseBody.receipt());

                assertEquals("77115870071", responseBody.customerCpf());
                assertEquals("Celta", responseBody.model());
        }

        @Test
        public void getByReceipt_InvalidReceipt_ReturnErrorMessageWithNotFoundStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parkings/check-in/{receipt}", "000-0000-00000000-000000")
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
        public void checkOut_ValidReceipt_ReturnCustomerParkingSpotWithOkStatus() {
                var responseBody = webTestClient
                                .put()
                                .uri("/api/v1/parkings/check-out/{receipt}", "AAA-1111-20250102-000000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody(ParkingResponseDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertNotNull(responseBody.parkingSpotCode());
                assertNotNull(responseBody.receipt());
                assertNotNull(responseBody.exitAt());
                assertNotNull(responseBody.price());
                assertNotNull(responseBody.descount());

                assertEquals("77115870071", responseBody.customerCpf());
                assertEquals("Celta", responseBody.model());
        }

        @Test
        public void checkOut_InvalidReceipt_ReturnErrorMessageWithNotFoundStatus() {
                var responseBody = webTestClient
                                .put()
                                .uri("/api/v1/parkings/check-out/{receipt}", "000-0000-00000000-000000")
                                .contentType(MediaType.APPLICATION_JSON)
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
        public void checkOut_AsCustomer_ReturnErrorMessageWithForbbidenStatus() {
                var responseBody = webTestClient
                                .put()
                                .uri("/api/v1/parkings/check-out/{receipt}", "AAA-1111-20250102-000000")
                                .contentType(MediaType.APPLICATION_JSON)
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
        public void getAllByCpf_ValidCpf_ReturnCustomerParkingListWithOkStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parkings/cpf/77115870071")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBodyList(PageableDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(1, responseBody.size());
        }

        @Test
        public void getAllByCpf_AsCustomer_ReturnErrorMessageWithForbbidenStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parkings/cpf/77115870071")
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
        public void getAllByCustomerUserId_AsCustomer_ReturnCustomerParkingListWithOkStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parkings")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "customer", "123456"))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBodyList(PageableDto.class)
                                .returnResult()
                                .getResponseBody();

                assertNotNull(responseBody);
                assertEquals(1, responseBody.size());
        }

        @Test
        public void getAllByCustomerUserId_AsAdmin_ReturnErrorMessageWithForbbidenStatus() {
                var responseBody = webTestClient
                                .get()
                                .uri("/api/v1/parkings")
                                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "admin", "123456"))
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
