package com.frostetsky.weather.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
        @NotEmpty(message = "Username should not be empty")
        @Size(min = 3, max = 20, message = "Username should be between 3 and 20 characters")
        String username,
        @NotEmpty(message = "Password should not be empty")
        @Size(min = 3, max = 20, message = "Password should be between 3 and 20 characters")
        String password,
        @NotEmpty(message = "Password should not be empty")
        @Size(min = 3, max = 20, message = "Password should be between 3 and 20 characters")
        String repeatPassword) {
}
