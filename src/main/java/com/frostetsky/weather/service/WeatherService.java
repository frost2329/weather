package com.frostetsky.weather.service;

import com.frostetsky.weather.db.entity.Location;
import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.OpenWeatherDto;
import com.frostetsky.weather.dto.WeatherCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final LocationService locationService;
    private final OpenWeatherClient openWeatherClient;

    public List<WeatherCardDto> getWeatherCardsForUser(Long userId) {
        List<Location> locations = locationService.getLocationsByUser(userId);
        List<WeatherCardDto> weatherCards = new ArrayList<>();
        for (Location location : locations) {
            OpenWeatherDto weatherDto = openWeatherClient.getWeatherByCoordinates(location.getLongitude(), location.getLatitude());
            WeatherCardDto weatherCard = new WeatherCardDto(
                    location.getId(),
                    weatherDto.name(),
                    weatherDto.sys().country(),
                    weatherDto.main().temp(),
                    weatherDto.main().tempFeelsLike(),
                    weatherDto.main().humidity(),
                    weatherDto.weather().getFirst().main(),
                    "https://openweathermap.org/img/wn/%s@4x.png".formatted(weatherDto.weather().getFirst().icon())
            );
            weatherCards.add(weatherCard);
        }
        return weatherCards;
    }

    public List<LocationDto> getLocationsByCityName(String cityName) {
        return openWeatherClient.getLocationsByCityName(cityName);
    }

}
