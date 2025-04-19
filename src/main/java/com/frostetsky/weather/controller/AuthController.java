package com.frostetsky.weather.controller;

import com.frostetsky.weather.dto.UserCreateDto;
import com.frostetsky.weather.dto.UserLoginDto;
import com.frostetsky.weather.exception.IncorrectPasswordException;
import com.frostetsky.weather.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/registration")
    public String showRegistration(@ModelAttribute("user") UserCreateDto user) {
        return "sign-up";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid UserCreateDto user,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "sign-up";
        }
        if(!user.password().equals(user.repeatPassword())) {
            throw new IncorrectPasswordException();
        }
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin(@ModelAttribute("user") UserLoginDto user) {
        return "sign-in";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid UserLoginDto user,
                        BindingResult bindingResult,
                        HttpServletResponse resp) {
        if(bindingResult.hasErrors()) {
            return "sign-in";
        }

        String session = userService.login(user);

        Cookie cookie = new Cookie("SESSION", session);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);

        resp.addCookie(cookie);
        return "redirect:index";
    }


}
