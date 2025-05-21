package com.frostetsky.weather.exception;

public class LocationServiceException extends RuntimeException{
    public LocationServiceException(String message) {
        super(message);
    }

    public LocationServiceException(String message, Exception e) {
        super(message, e);
    }
}
