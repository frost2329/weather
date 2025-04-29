package com.frostetsky.weather.dto;

public record WeatherCardDto(
        Long locationId,
        String city,
        String country,
        Double temperature,
        Double temperatureFeelsLike,
        Integer humidity,
        String description,
        String icon
) {
    public static WeatherCardDto createEmptyCard(Long locationId, String city) {
        return new WeatherCardDto(
                locationId,
                city,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
