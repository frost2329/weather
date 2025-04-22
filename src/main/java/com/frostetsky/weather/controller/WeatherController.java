package com.frostetsky.weather.controller;

import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.service.SessionService;
import com.frostetsky.weather.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final UserService userService;
    private final SessionService sessionService;
    @GetMapping
    public String main() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String index(@CookieValue(value = "MYSESSIONID", required = false) String sessionId, Model model) {
        if (sessionId == null) {
            return "index";
        }
        //todo validate session
        UserReadDto user = userService.getUserIdBySession(UUID.fromString(sessionId));
        model.addAttribute("user", user);
        return "index";
    }
}
