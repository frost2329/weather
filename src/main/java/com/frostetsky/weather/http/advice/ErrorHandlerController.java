package com.frostetsky.weather.http.advice;

import com.frostetsky.weather.dto.UserCreateDto;
import com.frostetsky.weather.dto.UserLoginDto;
import com.frostetsky.weather.exception.LocationServiceException;
import com.frostetsky.weather.exception.WeatherServiceException;
import com.frostetsky.weather.exception.auth.AuthenticationUserException;
import com.frostetsky.weather.exception.registration.CreateUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.frostetsky.weather.http.controller.AuthController.LOGIN_PAGE;
import static com.frostetsky.weather.http.controller.RegistrationController.REGISTRATION_PAGE;

@Slf4j
@ControllerAdvice(basePackages = "com.frostetsky.weather.http.controller")
public class ErrorHandlerController {

    public static final String ERROR_PAGE = "error";

    @ExceptionHandler(CreateUserException.class)
    public String handleCreateUserException(CreateUserException e, Model model) {
        log.error("Ошибка при создании пользователя: {}", e.getMessage(), e);
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("user", new UserCreateDto("", "", ""));
        return REGISTRATION_PAGE;
    }

    @ExceptionHandler(AuthenticationUserException.class)
    public String handleAuthenticationException(AuthenticationUserException e, Model model) {
        log.error("Ошибка аутентификации пользователя: {}", e.getMessage(), e);
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("user", new UserLoginDto("", ""));
        return LOGIN_PAGE;
    }

    @ExceptionHandler(LocationServiceException.class)
    public String handleLocationServiceException(LocationServiceException e, Model model) {
        log.error("Ошибка в LocationService: {}", e.getMessage(), e);
        model.addAttribute("errorMessage", e.getMessage());
        return ERROR_PAGE;
    }

    @ExceptionHandler(WeatherServiceException.class)
    public String handleWeatherServiceException(WeatherServiceException e, Model model) {
        log.error("Ошибка в WeatherService: {}", e.getMessage(), e);
        model.addAttribute("errorMessage", e.getMessage());
        return ERROR_PAGE;
    }

    @ExceptionHandler(Exception.class)
    public String handleOtherException(Exception e, Model model) {
        log.error("Получена непредвиденная ошибка {}", e.getMessage(), e);
        model.addAttribute("errorMessage",
                "Извините, но произошла непредвиденная ошибка. Попробуйте еще раз позже.");
        return ERROR_PAGE;
    }
}
