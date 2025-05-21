package com.frostetsky.weather.http.controller;

import com.frostetsky.weather.dto.UserLoginDto;
import com.frostetsky.weather.service.SessionService;
import com.frostetsky.weather.service.UserService;
import com.frostetsky.weather.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.frostetsky.weather.util.CookieUtil.COOKIE_SESSION;

@Controller
@RequiredArgsConstructor
public class AuthController {

    public static final String LOGIN_PAGE = "sign-in";

    private final UserService userService;
    private final SessionService sessionService;

    @GetMapping("/login")
    public String showLogin(@ModelAttribute("user") UserLoginDto user) {
        return "sign-in";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid UserLoginDto user,
                        BindingResult bindingResult,
                        HttpServletResponse resp) {
        if (bindingResult.hasErrors()) {
            return LOGIN_PAGE;
        }
        String session = userService.authenticateUser(user);
        ResponseCookie cookie = CookieUtil.createSessionCookie(session);
        resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "redirect:home";
    }

    @PostMapping("/logout")
    public String logout(@CookieValue(value = COOKIE_SESSION, required = false) String sessionId,
                         HttpServletResponse resp) {
        try {
            sessionService.removeSession(UUID.fromString(sessionId));
        } catch (Exception e) {
            // todo log
        }
        ResponseCookie deleteCookie = CookieUtil.createDeleteCookie();
        resp.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
        return "redirect:login";
    }
}
