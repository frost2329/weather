package com.frostetsky.weather.dto.mapper;

import com.frostetsky.weather.dto.OpenWeatherDto;
import com.frostetsky.weather.dto.WeatherCardDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeatherMapper {

    String IMAGE_SRC = "https://openweathermap.org/img/wn/%s@4x.png";

    @Mapping(target = "locationId", source = "locationId")
    @Mapping(target = "city", source = "openWeatherDto.name")
    @Mapping(target = "country", source = "openWeatherDto.sys.country")
    @Mapping(target = "temperature", source = "openWeatherDto.main.temp")
    @Mapping(target = "temperatureFeelsLike", source = "openWeatherDto.main.tempFeelsLike")
    @Mapping(target = "humidity", source = "openWeatherDto.main.humidity")
    @Mapping(target = "description", source = "openWeatherDto", qualifiedByName = "getFirstWeatherMain")
    @Mapping(target = "icon", source = "openWeatherDto", qualifiedByName = "getFormattedIcon")
    WeatherCardDto toDto(OpenWeatherDto openWeatherDto, Long locationId);

    @Named("getFirstWeatherMain")
    default String getFirstWeatherMain(OpenWeatherDto dto) {
        try {
            return dto.weather().getFirst().main();
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Named("getFormattedIcon")
    default String getFormattedIcon(OpenWeatherDto dto) {
        try {
            return IMAGE_SRC.formatted(dto.weather().getFirst().icon());
        } catch (RuntimeException e) {
            return null;
        }
    }
}
