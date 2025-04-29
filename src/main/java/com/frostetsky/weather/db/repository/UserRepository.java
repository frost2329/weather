package com.frostetsky.weather.db.repository;

import com.frostetsky.weather.db.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserRepository {
    @PersistenceContext
    private  EntityManager entityManager;

    private final String HQL_GET_USER_BY_LOGIN = "SELECT u FROM User u WHERE u.login = :login";

    @Transactional
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String username) {
        return Optional.ofNullable(entityManager.createQuery(HQL_GET_USER_BY_LOGIN, User.class)
                .setParameter("login", username)
                .getSingleResult());
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
}
