package com.frostetsky.weather.service;

import com.frostetsky.weather.db.entity.Session;
import com.frostetsky.weather.db.entity.User;
import com.frostetsky.weather.db.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    @Transactional
    public Session createSession(User user) {
        Session session = Session.builder().userId(user.getId()).build();
        return sessionRepository.save(session);
    }

    @Transactional
    public void removeSession(UUID sessionId) {
        sessionRepository.delete(sessionId);
    }

    @Transactional(readOnly = true)
    public Optional<Session> getSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId);
    }
}
