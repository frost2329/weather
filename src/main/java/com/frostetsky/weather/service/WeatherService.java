package com.frostetsky.weather.service;

import com.frostetsky.weather.db.entity.Location;
import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.OpenWeatherDto;
import com.frostetsky.weather.dto.WeatherCardDto;
import com.frostetsky.weather.dto.mapper.WeatherMapper;
import com.frostetsky.weather.exception.OpenWeatherApiException;
import com.frostetsky.weather.exception.WeatherServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final LocationService locationService;
    private final OpenWeatherClient openWeatherClient;
    private final WeatherMapper weatherMapper;

    @Transactional(readOnly = true)
    public List<WeatherCardDto> getWeatherCardsForUser(Long userId) {
        try {
            return locationService.getLocationsByUser(userId).stream()
                    .map(this::getWeatherCardByLocation)
                    .toList();
        } catch (Exception e) {
            throw new WeatherServiceException("Произошла ошибка при получении данных о погоде", e);
        }
    }

    private WeatherCardDto getWeatherCardByLocation(Location location) {
        try {
            OpenWeatherDto weatherDto = openWeatherClient.getWeatherByCoordinates(
                    location.getLongitude(),
                    location.getLatitude());
            return weatherMapper.toDto(weatherDto, location.getId());
        } catch (OpenWeatherApiException e) {
            return WeatherCardDto.createEmptyCard(location.getId(), location.getName());
        }
    }

    public List<LocationDto> getLocationsByCityName(String cityName) {
        try {
            return openWeatherClient.getLocationsByCityName(cityName);
        } catch (OpenWeatherApiException e) {
            throw new WeatherServiceException(e.getMessage(), e);
        }
    }
}
