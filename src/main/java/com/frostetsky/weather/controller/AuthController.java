package com.frostetsky.weather.controller;

import com.frostetsky.weather.dto.UserCreateDto;
import com.frostetsky.weather.dto.UserLoginDto;
import com.frostetsky.weather.exception.IncorrectPasswordException;
import com.frostetsky.weather.service.SessionService;
import com.frostetsky.weather.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final SessionService sessionService;

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

        ResponseCookie cookie = ResponseCookie.from("MYSESSIONID", session)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(12))
                .sameSite("Strict")
                .build();

        resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return "redirect:home";
    }

    @PostMapping("/logout")
    public String logout(@CookieValue(value = "MYSESSIONID", required = false) String sessionId,
                         HttpServletResponse resp) {
        if (sessionId != null) {
            // todo validate sessionID
            sessionService.removeSession(UUID.fromString(sessionId));

            ResponseCookie deleteCookie = ResponseCookie.from("MYSESSIONID", sessionId)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(0)
                    .sameSite("Strict")
                    .build();
            resp.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
        }
        return "redirect:login";
    }
}
