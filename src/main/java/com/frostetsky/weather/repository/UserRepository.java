package com.frostetsky.weather.repository;

import com.frostetsky.weather.entity.User;
import com.frostetsky.weather.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public User save(User user) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public Optional<User> getUserByLogin(String username) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Optional<User> user = session.createQuery("select u from User u where u.login = :login", User.class)
                    .setParameter("login", username)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return user;
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
