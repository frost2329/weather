package com.frostetsky.weather.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieUtil {
    public static final String COOKIE_SESSION = "MYSESSIONID";

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookieName != null) {
            for (Cookie cookie : cookies) {
                if(cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static ResponseCookie createSessionCookie(String session) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_SESSION, session)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(12))
                .sameSite("Strict")
                .build();
        return cookie;
    }

    public static ResponseCookie createDeleteCookie() {
        ResponseCookie deleteCookie = ResponseCookie.from(COOKIE_SESSION, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        return deleteCookie;
    }

}
