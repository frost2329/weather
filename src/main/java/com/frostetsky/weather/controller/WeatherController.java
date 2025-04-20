package com.frostetsky.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {
    @GetMapping
    public String main() {
        return "redirect:index";
    }

    @GetMapping("/index")
    public String index(@CookieValue(value = "MYSESSIONID", required = false) String sessionId) {
        if (sessionId == null) {
            return "redirect:/login";
        }
        return "index";
    }
}
