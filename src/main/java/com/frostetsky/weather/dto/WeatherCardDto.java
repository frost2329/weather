package com.frostetsky.weather.dto;

public record WeatherCardDto (
        Long locationId,
        String city,
        String country,
        Double temperature,
        Double temperatureFeelsLike,
        Integer humidity,
        String description,
        String icon
) {}
