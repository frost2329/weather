package com.frostetsky.weather.exception;

public class WeatherServiceException extends RuntimeException{
    public WeatherServiceException(String message) {
        super(message);
    }

    public WeatherServiceException(String message, Exception e) {
        super(message, e);
    }
}
