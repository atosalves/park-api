package com.atosalves.park_api.exception;

public class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message) {
                super(message);
        }
}
