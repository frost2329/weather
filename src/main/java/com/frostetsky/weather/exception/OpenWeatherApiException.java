package com.frostetsky.weather.exception;

import lombok.Getter;

@Getter
public class OpenWeatherApiException extends RuntimeException {

    public OpenWeatherApiException(String message) {
        super(message);
    }

    public OpenWeatherApiException(String message, Exception e) {
        super(message, e);
    }
}
