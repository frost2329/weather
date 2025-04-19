package com.frostetsky.weather.service;

import com.frostetsky.weather.entity.Session;
import com.frostetsky.weather.entity.User;
import com.frostetsky.weather.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    public Session createSession(User user) {
        Session session = Session.builder().user(user).build();
        return sessionRepository.save(session);
    }
}
