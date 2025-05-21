package com.frostetsky.weather.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.OpenWeatherDto;
import com.frostetsky.weather.exception.OpenWeatherApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Component
@Slf4j
public class OpenWeatherClient {
    private static final String URL_LOCATIONS_PATH = "/geo/1.0/direct";
    private static final String URL_WEATHER_PATH = "/data/2.5/weather";
    private static final String API_KEY = "b2687d0446a9bd2d9e0339b6af7bb9b5";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    @Autowired
    public OpenWeatherClient(@Value("${openweather.api.url}") String baseUrl,
                             ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        this.objectMapper = objectMapper;
    }

    public List<LocationDto> getLocationsByCityName(String cityName) {
        log.info("Запрос локаций по городу {}", cityName);
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(URL_LOCATIONS_PATH)
                .queryParam("q", cityName)
                .queryParam("limit", 10)
                .queryParam("appid", API_KEY)
                .build()
                .toUri();
        List<LocationDto> location = executeRequest(uri, new TypeReference<List<LocationDto>>() {});
        if (location.isEmpty()) {
            throw new OpenWeatherApiException("Локации не найдены");
        }
        return location;
    }

    public OpenWeatherDto getWeatherByCoordinates(double longitude, double latitude) {
        log.info("Запрос погоды по координатам lon : {}, lat : {}", longitude, latitude);
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(URL_WEATHER_PATH)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("units", "metric")
                .queryParam("appid", API_KEY)
                .build()
                .toUri();
        return executeRequest(uri, new TypeReference<OpenWeatherDto>() {});
    }

    private <T> T executeRequest(URI uri, TypeReference<T> typeReference) {
        log.info("Выполнение запроса к {}", uri);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Получен ответ: статус {}, тело ответа: {}", response.statusCode(), response.body());
            if (response.statusCode() != HttpStatus.OK.value()) {
                log.error("Ошибка API. Статус {}, Тело ответа {}", response.statusCode(), response.body());
                throw new OpenWeatherApiException("Неожиданный статус ответа : " + response.statusCode());
            }
            return objectMapper.readValue(response.body(), typeReference);
        } catch (InterruptedException e) {
            log.error("Запрос был прерван: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new OpenWeatherApiException("Request was interrupted", e);
        } catch (IOException e) {
            log.error("Неизвестная ошибка при выполнении запроса: {}", e.getMessage(), e);
            throw new OpenWeatherApiException("Ошибка при выполнении запроса", e);
        }
    }
}
