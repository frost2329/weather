package com.frostetsky.weather.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frostetsky.weather.db.entity.Location;
import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.OpenWeatherDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class OpenWeatherClient {
    private final String URL_GEO_LOCATIONS = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=10&appid=%s";
    private final String URL_WEATHER = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric&appid=%s";
    private final String API_KEY = "b2687d0446a9bd2d9e0339b6af7bb9b5";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public List<LocationDto> getLocationsByCityName(String cityName) {
        String url = URL_GEO_LOCATIONS.formatted(cityName, API_KEY);
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            List<LocationDto> locations = objectMapper.readValue(response.body(),
                    new TypeReference<List<LocationDto>>(){});
            return locations;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public OpenWeatherDto getWeatherByCoordinates(double longitude, double latitude) {
        String url = URL_WEATHER.formatted(latitude, longitude, API_KEY);
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            OpenWeatherDto weatherCardDto = objectMapper.readValue(response.body(), new TypeReference<OpenWeatherDto>(){});
            return weatherCardDto;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
