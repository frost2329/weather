package com.frostetsky.weather.http.advice;

import com.frostetsky.weather.dto.UserCreateDto;
import com.frostetsky.weather.dto.UserLoginDto;
import com.frostetsky.weather.exception.auth.AuthenticationUserException;
import com.frostetsky.weather.exception.registration.CreateUserException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.frostetsky.weather.http.controller")
public class ErrorHandlerController {

    @ExceptionHandler(CreateUserException.class)
    public String handleCreateUserException(CreateUserException e, Model model) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("user", new UserCreateDto("", "", ""));
        return "sign-up";
    }

    @ExceptionHandler(AuthenticationUserException.class)
    public String handleAuthenticationException(AuthenticationUserException e, Model model) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("user", new UserLoginDto("", ""));
        return "sign-in";
    }
}
