package com.frostetsky.weather.exception.registration;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends CreateUserException {
    public PasswordMismatchException() {
        super("Пароли не совпадают");
    }
}
