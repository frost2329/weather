package com.frostetsky.weather.db.repository;

import com.frostetsky.weather.db.entity.Session;
import com.frostetsky.weather.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository
public class SessionRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public Session save(Session mySession) {
        try (var session = sessionFactory.openSession()) {
            session.persist(mySession);
            return mySession;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public boolean deleteById(UUID id) {
        try (var session = sessionFactory.openSession()) {
            session.remove(session.get(Session.class, id));
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional(readOnly = true)
    public Optional<Session> findById(UUID id) {
        try (var session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Session.class, id));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
