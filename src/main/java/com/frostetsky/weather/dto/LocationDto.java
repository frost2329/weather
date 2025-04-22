package com.frostetsky.weather.dto;

public record LocationDto(Long id,
                          String name,
                          Double latitude,
                          Double longitude) {
}
