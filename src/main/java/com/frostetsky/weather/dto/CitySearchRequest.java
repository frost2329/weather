package com.frostetsky.weather.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CitySearchRequest(
        @NotEmpty(message = "Введите название города для поиска")
        @Size(min = 3, max = 20, message = "Название города должно быть от 3 до 20 символов")
        @Pattern(regexp = "^(?!.*[-\\s']{2})[A-Za-zа-яА-Я]+(?:[-\\s'][A-Za-zа-яА-Я]+)*$",
                message = "Символы не допустимы для поиска города")
        String cityName) {
}
