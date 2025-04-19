package com.frostetsky.weather.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidCredentialsException extends RuntimeException {

  private int statusCode;

    public InvalidCredentialsException() {
        super("Invalid username or password");
        statusCode = 401;
    }
}
