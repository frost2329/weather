package com.frostetsky.weather.db.repository;

import com.frostetsky.weather.db.entity.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository
public class SessionRepository {
    @PersistenceContext
    private  EntityManager entityManager;

    public Session save(Session mySession) {
        entityManager.persist(mySession);
        return mySession;
    }

    public boolean delete(UUID id) {
        entityManager.remove(entityManager.find(Session.class, id));
        return true;
    }

    public Optional<Session> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Session.class, id));
    }
}
