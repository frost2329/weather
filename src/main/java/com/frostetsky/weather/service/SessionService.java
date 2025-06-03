package com.frostetsky.weather.service;

import com.frostetsky.weather.db.entity.Session;
import com.frostetsky.weather.db.entity.User;
import com.frostetsky.weather.db.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Scheduled(fixedRate = 360000)
    @Transactional
    public void clearExpiredSession() {
        log.info("Starting expired sessions cleanup...");
        try {
            int deletedCount = sessionRepository.deleteExpiredSessions();
            log.info("Deleted {} expired sessions", deletedCount);
        } catch (Exception e) {
            log.error("Error during session cleanup", e);
        }
    }
}
