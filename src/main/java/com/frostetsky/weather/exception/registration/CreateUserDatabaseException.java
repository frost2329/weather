package com.frostetsky.weather.exception.registration;

public class CreateUserDatabaseException extends CreateUserException {
    private static final String DEFAULT_MSG = "Ошибка при сохранении пользователя в базу данных";

    public CreateUserDatabaseException() {
        super(DEFAULT_MSG);
    }

    public CreateUserDatabaseException(Exception e) {
        super(DEFAULT_MSG, e);
    }
}
