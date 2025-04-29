package com.frostetsky.weather.exception.auth;

public class AuthenticationUserException extends RuntimeException {
    private static final String DEFAULT_MSG = "Ошибка аутентификации";

    public AuthenticationUserException(Exception e) {
        this(DEFAULT_MSG, e);
    }

    public AuthenticationUserException(String message, Exception e) {
        super(message, e);
    }

    public AuthenticationUserException(String message) {
        super(message);
    }
}
