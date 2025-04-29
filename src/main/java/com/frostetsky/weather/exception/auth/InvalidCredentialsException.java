package com.frostetsky.weather.exception.auth;

import lombok.Getter;

@Getter
public class InvalidCredentialsException extends AuthenticationUserException {

    public InvalidCredentialsException() {
        this("Некорректное имя пользователя или пароль");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
