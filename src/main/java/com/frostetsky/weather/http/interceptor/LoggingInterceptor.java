package com.frostetsky.weather.http.interceptor;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Получен запрос. Method: {}. URL: {}. Query Params: {}",
                request.getMethod(), request.getRequestURL(), request.getQueryString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        log.info("Запрос обработан. Status: {}", response.getStatus());
        if(e != null) {
            log.error("Ошибка при обработке запроса ", e);
        }
    }
}
