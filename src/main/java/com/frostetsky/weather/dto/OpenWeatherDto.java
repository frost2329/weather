package com.frostetsky.weather.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenWeatherDto(
        String name,
        Main main,
        List<Weather> weather,
        Sys sys
) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Main(
                double temp,
                @JsonProperty("feels_like")
                double tempFeelsLike,
                int humidity
        ) {}

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Weather(
                String main,
                String icon
        ) {}

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Sys(String country) {}
}
