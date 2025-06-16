package com.frostetsky.weather;

import com.frostetsky.weather.config.LiquibaseTestConfig;
import com.frostetsky.weather.config.TestConfig;
import com.frostetsky.weather.db.entity.Location;
import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.WeatherCardDto;
import com.frostetsky.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;


@SpringJUnitConfig({TestConfig.class, LiquibaseTestConfig.class})
class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    void checkGetLocationByName() {
        List<LocationDto> locations = weatherService.getLocationsByCityName("Moscow");
        assert locations.stream().anyMatch(l -> "moscow".equalsIgnoreCase(l.name()));
    }

    @Test
    void checkGetWeatherCard() {
        List<LocationDto> locations = weatherService.getLocationsByCityName("Moscow");
        LocationDto moscowLocation = locations.stream()
                .filter(l -> "moscow".equalsIgnoreCase(l.name()))
                .findFirst()
                .orElseThrow();

        Location location = Location.builder()
                .userId(123L)
                .latitude(Double.parseDouble(moscowLocation.lat()))
                .longitude(Double.parseDouble(moscowLocation.lon()))
                .name(moscowLocation.name()).build();
        WeatherCardDto weatherCardByLocation = weatherService.getWeatherCardByLocation(location);

        assert "moscow".equalsIgnoreCase(weatherCardByLocation.city());

    }
}

