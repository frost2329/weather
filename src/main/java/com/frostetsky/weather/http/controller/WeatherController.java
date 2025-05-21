package com.frostetsky.weather.http.controller;

import com.frostetsky.weather.dto.CitySearchRequest;
import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.dto.WeatherCardDto;
import com.frostetsky.weather.service.LocationService;
import com.frostetsky.weather.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    public static final String HOME_PAGE = "index";
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
        return HOME_PAGE;
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("city") @Valid CitySearchRequest cityRequest,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            String searchErrorMessage = result.getFieldError().getDefaultMessage();
            redirectAttributes.addFlashAttribute("searchErrorMessage", searchErrorMessage);
            return "redirect:/home";
        }
        List<LocationDto> locations = weatherService.getLocationsByCityName(cityRequest.cityName());
        model.addAttribute("locations", locations);
        return HOME_PAGE;
    }

    @PostMapping("/add-location")
    public String addLocation(HttpServletRequest request,
                              @ModelAttribute("location") LocationDto location) {
        UserReadDto user = (UserReadDto) request.getAttribute("user");
        locationService.addLocationForUser(location, user.id());
        return "redirect:home";
    }

    @PostMapping("/delete-location")
    public String addLocation(HttpServletRequest request,
                              @RequestParam("locationId") Long locationId) {
        UserReadDto user = (UserReadDto) request.getAttribute("user");
        locationService.removeLocation(locationId, user.id());
        return "redirect:home";
    }
}
