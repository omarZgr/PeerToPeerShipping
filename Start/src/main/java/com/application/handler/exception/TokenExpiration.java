package com.application.handler.exception;

public class TokenExpiration extends RuntimeException {
    public TokenExpiration(String s) {
        super(s);
    }
}
