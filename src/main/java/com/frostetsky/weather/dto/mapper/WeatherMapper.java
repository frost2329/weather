package com.frostetsky.weather.dto.mapper;

import com.frostetsky.weather.dto.OpenWeatherDto;
import com.frostetsky.weather.dto.WeatherCardDto;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {
    private final static String IMAGE_SRC = "https://openweathermap.org/img/wn/%s@4x.png";

    public WeatherCardDto toDto(OpenWeatherDto openWeatherDto, Long locationId) {
        WeatherCardDto weatherCard = new WeatherCardDto(
                locationId,
                openWeatherDto.name(),
                openWeatherDto.sys().country(),
                openWeatherDto.main().temp(),
                openWeatherDto.main().tempFeelsLike(),
                openWeatherDto.main().humidity(),
                openWeatherDto.weather().getFirst().main(),
                IMAGE_SRC.formatted(openWeatherDto.weather().getFirst().icon()));
        return  weatherCard;
    }
}
