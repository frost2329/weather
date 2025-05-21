package com.frostetsky.weather.http.interceptor;

import com.frostetsky.weather.service.UserService;
import com.frostetsky.weather.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PublicPageInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = CookieUtil.getCookieValue(request, CookieUtil.COOKIE_SESSION);
        if (sessionId == null) {
            return true;
        }
        try {
            userService.getUserIdBySession(UUID.fromString(sessionId));
            response.sendRedirect("/");
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
