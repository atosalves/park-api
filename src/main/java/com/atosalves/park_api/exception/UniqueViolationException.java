package com.atosalves.park_api.exception;

public class UniqueViolationException extends RuntimeException {
        public UniqueViolationException(String message) {
                super(message);
        }
}
