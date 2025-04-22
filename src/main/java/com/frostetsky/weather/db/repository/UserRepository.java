package com.frostetsky.weather.db.repository;

import com.frostetsky.weather.db.entity.User;
import com.frostetsky.weather.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final String HQL_GET_USER_BY_LOGIN = "select u from User u where u.login = :login";
    private final String HQL_GET_USER_BY_SESSION_WITH_LOCATIONS = """
            select u
            from User u
            join u.sessions s
            left join fetch u.locations
            where s.id = :sessionId
            and s.expiresAt >= current_timestamp
            order by s.expiresAt desc
            """;

    public User save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
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
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Optional<User> user = session.createQuery(HQL_GET_USER_BY_LOGIN, User.class)
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

    public Optional<User> findUserBySession(UUID sessionId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Optional<User> user = session.createQuery(HQL_GET_USER_BY_SESSION_WITH_LOCATIONS, User.class)
                    .setParameter("sessionId", sessionId)
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

    public Optional<User> findById(Long id) {
        try (var session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
