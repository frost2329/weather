package com.frostetsky.weather.exception.registration;

public class UserAlreadyExistException extends CreateUserException {
    public UserAlreadyExistException(String login, Exception e) {
        super("Пользователь с логином %s уже существует".formatted(login), e);
    }
}
