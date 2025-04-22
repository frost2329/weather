package com.frostetsky.weather.service;

import com.frostetsky.weather.db.entity.Session;
import com.frostetsky.weather.db.entity.User;
import com.frostetsky.weather.db.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    public Session createSession(User user) {
        Session session = Session.builder().user(user).build();
        return sessionRepository.save(session);
    }

    public void removeSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
