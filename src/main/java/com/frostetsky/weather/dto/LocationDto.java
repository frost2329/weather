package com.frostetsky.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationDto(
        String name,
        String lat,
        String lon,
        String country,
        String state
){
}
