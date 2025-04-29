package com.frostetsky.weather.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.OpenWeatherDto;
import com.frostetsky.weather.exception.OpenWeatherApiException;
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
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(URL_LOCATIONS_PATH)
                .queryParam("q", cityName)
                .queryParam("limit", 10)
                .queryParam("appid", API_KEY)
                .build()
                .toUri();
        return executeRequest(uri, new TypeReference<List<LocationDto>>() {});
    }

    public OpenWeatherDto getWeatherByCoordinates(double longitude, double latitude) {
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
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new OpenWeatherApiException("Неожиданный статус ответа : " + response.statusCode());
            }
            return objectMapper.readValue(response.body(), typeReference);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OpenWeatherApiException("Request was interrupted", e);
        } catch (IOException e) {
            throw new OpenWeatherApiException("Ошибка при выполнении запроса", e);
        }
    }
}
