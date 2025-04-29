package com.frostetsky.weather.exception.registration;

public class CreateUserException extends RuntimeException {
    private static final String DEFAULT_MSG = "Ошибка при создании пользователя";

    public CreateUserException(Exception e) {
        this(DEFAULT_MSG, e);
    }

    public CreateUserException(String message) {
        super(message);
    }

    public CreateUserException(String message, Exception e) {
        super(message, e);
    }
}
