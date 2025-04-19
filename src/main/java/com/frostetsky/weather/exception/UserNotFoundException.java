package com.frostetsky.weather.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {

  private int statusCode;

    public UserNotFoundException() {
        super("Пользователь не найден");
        statusCode = 400;
    }
}
