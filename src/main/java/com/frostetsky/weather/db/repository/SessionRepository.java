package com.frostetsky.weather.db.repository;

import com.frostetsky.weather.db.entity.Session;
import com.frostetsky.weather.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public class SessionRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Session save(Session mySession) {
        Transaction transaction = null;
        try(var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(mySession);
            transaction.commit();
            return mySession;
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(UUID id) {
        Transaction transaction = null;
        try (var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(Session.class, id));
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
