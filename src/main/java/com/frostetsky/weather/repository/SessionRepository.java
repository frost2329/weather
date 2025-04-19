package com.frostetsky.weather.repository;

import com.frostetsky.weather.entity.Session;
import com.frostetsky.weather.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class SessionRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Session save(Session session) {
        Transaction transaction = null;
        try(var entityManger = sessionFactory.openSession()) {
            transaction = entityManger.beginTransaction();
            entityManger.save(session);
            transaction.commit();
            return session;
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
