package com.frostetsky.weather.dto;

import java.util.List;

public record UserReadDto (Long id, String login, List<LocationDto> locations) {

}
