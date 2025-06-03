package com.frostetsky.weather.db.repository;

import com.frostetsky.weather.db.entity.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;


@Repository
public class SessionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final String HQL_DELETE_EXPIRED_SESSIONS = "DELETE FROM Session s WHERE s.expiresAt < :now";

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

    public int deleteExpiredSessions() {
        int deletedCount = entityManager.createQuery(HQL_DELETE_EXPIRED_SESSIONS)
                .setParameter("now", new Timestamp(System.currentTimeMillis()))
                .executeUpdate();
        return deletedCount;
    }
}
