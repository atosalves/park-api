package com.atosalves.park_api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.atosalves.park_api.exception.EntityNotFoundException;
import com.atosalves.park_api.exception.PasswordInvalidException;
import com.atosalves.park_api.exception.UniqueViolationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.MethodArgumentNotValidException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException exception,
                        HttpServletRequest request, BindingResult result) {
                log.error("Api Error - ", exception);
                return ResponseEntity
                                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY,
                                                "Campo(s) inválido(s)", result));
        }

        @ExceptionHandler(UniqueViolationException.class)
        public ResponseEntity<ErrorMessage> uniqueViolationException(UniqueViolationException exception,
                        HttpServletRequest request) {
                log.error("Api Error - ", exception);
                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.CONFLICT,
                                                exception.getMessage()));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException exception,
                        HttpServletRequest request) {
                log.error("Api Error - ", exception);
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND,
                                                exception.getMessage()));
        }

        @ExceptionHandler(PasswordInvalidException.class)
        public ResponseEntity<ErrorMessage> passwordInvalidException(PasswordInvalidException exception,
                        HttpServletRequest request) {
                log.error("Api Error - ", exception);
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                                                exception.getMessage()));
        }
}
