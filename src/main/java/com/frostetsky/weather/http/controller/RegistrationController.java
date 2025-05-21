package com.frostetsky.weather.http.controller;

import com.frostetsky.weather.dto.UserCreateDto;
import com.frostetsky.weather.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class RegistrationController {
    public static final String REGISTRATION_PAGE = "sign-up";
    private final UserService userService;

    @GetMapping("/registration")
    public String showRegistration(@ModelAttribute("user") UserCreateDto user) {
        return "sign-up";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid UserCreateDto user,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return REGISTRATION_PAGE;
        }
        userService.createUser(user);
        return "redirect:/login";
    }
}
