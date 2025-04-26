package com.frostetsky.weather.controller;

import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.dto.WeatherCardDto;
import com.frostetsky.weather.service.LocationService;
import com.frostetsky.weather.service.UserService;
import com.frostetsky.weather.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final LocationService locationService;

    @GetMapping
    public String main() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String index(HttpServletRequest request,
                        Model model) {

        UserReadDto user = (UserReadDto) request.getAttribute("user");

        List<WeatherCardDto> weatherCards = weatherService.getWeatherCardsForUser(user.id());
        model.addAttribute("weatherCards", weatherCards);
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("city") String city,
                         @CookieValue(value = "MYSESSIONID", required = false) String sessionId,
                         Model model) {
        List<LocationDto> locations = weatherService.getLocationsByCityName(city);
        model.addAttribute("locations", locations);
        return "index";
    }

    @PostMapping("/add-location")
    public String addLocation(HttpServletRequest request,
                              @ModelAttribute("location") LocationDto location) {
        //todo validate location
        UserReadDto user = (UserReadDto) request.getAttribute("user");
        locationService.addLocationForUser(location, user.id());
        return "redirect:home";
    }

    @PostMapping("/delete-location")
    public String addLocation(@RequestParam("locationId") Long locationId) {

        //todo get User
        //todo check user is owner of location
        locationService.removeLocation(locationId);
        return "redirect:home";
    }
}
