package com.atosalves.park_api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atosalves.park_api.jwt.JwtUserDetailsService;
import com.atosalves.park_api.web.dto.user.UserLoginDto;
import com.atosalves.park_api.web.exception.ErrorMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthController {

        private final JwtUserDetailsService jwtUserDetailsService;
        private final AuthenticationManager authenticationManager;

        @PostMapping("/auth")
        public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto loginDto, HttpServletRequest request) {

                try {
                        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                        loginDto.username(),
                                        loginDto.password());

                        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

                        var token = jwtUserDetailsService.getTokenAuthenticated(loginDto.username());

                        return ResponseEntity.ok(token);
                } catch (AuthenticationException e) {
                        log.warn("Credenciais inválidas");
                }
                return ResponseEntity
                                .badRequest()
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
        }

}
