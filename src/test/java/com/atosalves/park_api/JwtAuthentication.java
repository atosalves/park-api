package com.atosalves.park_api;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.atosalves.park_api.web.dto.TokenDto;
import com.atosalves.park_api.web.dto.user.UserLoginDto;

public class JwtAuthentication {

        public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username,
                        String password) {
                String token = client.post().uri("/api/v1/auth")
                                .bodyValue(new UserLoginDto(username, password))
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody(TokenDto.class)
                                .returnResult().getResponseBody().token();

                return header -> header.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }

}
