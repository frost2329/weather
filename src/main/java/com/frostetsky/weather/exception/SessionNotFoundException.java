package com.frostetsky.weather.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionNotFoundException extends RuntimeException {

  private int statusCode;

    public SessionNotFoundException() {
        super("Сессия не найдена");
        statusCode = 400;
    }
}
