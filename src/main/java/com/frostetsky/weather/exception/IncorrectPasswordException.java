package com.frostetsky.weather.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncorrectPasswordException extends RuntimeException {

  private int statusCode;

    public IncorrectPasswordException() {
        super("Passwords do not match");
        statusCode = 400;
    }
}
