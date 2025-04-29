package com.frostetsky.weather.http.interceptor;


import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.service.UserService;
import com.frostetsky.weather.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = CookieUtil.getCookieValue(request, CookieUtil.COOKIE_SESSION);
        if (sessionId == null) {
            response.sendRedirect("/login");
            return false;
        }
        try {
            UserReadDto user = userService.getUserIdBySession(UUID.fromString(sessionId));
            request.setAttribute("user", user);
            return true;
        } catch (Exception e) {
            ResponseCookie deleteCookie = CookieUtil.createDeleteCookie();
            response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
            response.sendRedirect("/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("user", request.getAttribute("user"));
        }
    }
}
