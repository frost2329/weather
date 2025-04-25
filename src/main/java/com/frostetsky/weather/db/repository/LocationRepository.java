package com.frostetsky.weather.db.repository;

import com.frostetsky.weather.db.entity.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LocationRepository {
    @PersistenceContext
    private  EntityManager entityManager;

    private final String HQL_GET_LOCATIONS_BY_USER = "select l from Location l where l.userId = :userId";

    @Transactional(readOnly = true)
    public List<Location> findLocationsByUser(Long userId) {
        return entityManager.createQuery(HQL_GET_LOCATIONS_BY_USER, Location.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Transactional
    public Location save(Location location) {
        entityManager.persist(location);
        return location;
    }

    @Transactional
    public void delete(Long id) {
        entityManager.remove(entityManager.find(Location.class, id));
    }
}
