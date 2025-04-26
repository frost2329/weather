package com.frostetsky.weather.controller;

import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.dto.WeatherCardDto;
import com.frostetsky.weather.service.LocationService;
import com.frostetsky.weather.service.UserService;
import com.frostetsky.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final UserService userService;
    private final WeatherService weatherService;
    private final LocationService locationService;

    @GetMapping
    public String main() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String index(@CookieValue(value = "MYSESSIONID", required = false) String sessionId,
                        Model model) {
        if (sessionId == null) {
            return "index";
        }
        //todo validate session

        UserReadDto user = userService.getUserIdBySession(UUID.fromString(sessionId));

        List<WeatherCardDto> weatherCards = weatherService.getWeatherCardsForUser(user.id());

        model.addAttribute("user", user);
        model.addAttribute("weatherCards", weatherCards);
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("city") String city,
                         @CookieValue(value = "MYSESSIONID", required = false) String sessionId,
                         Model model) {

        //todo validate city
        UserReadDto user = userService.getUserIdBySession(UUID.fromString(sessionId));
        model.addAttribute("user", user);

        List<LocationDto> locations = weatherService.getLocationsByCityName(city);
        model.addAttribute("locations", locations);
        return "index";
    }

    @PostMapping("/add-location")
    public String addLocation(@ModelAttribute("location") LocationDto location,
                              @CookieValue(value = "MYSESSIONID", required = false) String sessionId) {
        //todo validate session
        //todo validate location
        UserReadDto user = userService.getUserIdBySession(UUID.fromString(sessionId));
        locationService.addLocationForUser(location, user.id());
        return "redirect:home";
    }

    @PostMapping("/delete-location")
    public String addLocation(@RequestParam("locationId") Long locationId,
                              @CookieValue(value = "MYSESSIONID", required = false) String sessionId) {

        //todo validate session
        //todo get User by session
        //todo check user is owner of location
        locationService.removeLocation(locationId);
        return "redirect:home";
    }
}
