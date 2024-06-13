package com.kemsu.sibiryakov.api.JwtFilter.Exception;

public class InvalidToken extends RuntimeException {
    public InvalidToken(String message) {
        super(message);
    }
}

