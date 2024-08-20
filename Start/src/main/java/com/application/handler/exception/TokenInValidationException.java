package com.application.handler.exception;

public class TokenInValidationException extends RuntimeException {
    public TokenInValidationException(String s) {
        super(s);
    }
}
