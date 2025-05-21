package com.frostetsky.weather.db.repository;

import com.frostetsky.weather.db.entity.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository {
    @PersistenceContext
    private  EntityManager entityManager;

    private final String HQL_GET_LOCATIONS_BY_USER = "select l from Location l where l.userId = :userId";

    public Optional<Location> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Location.class, id));
    }

    public List<Location> findLocationsByUser(Long userId) {
        return entityManager.createQuery(HQL_GET_LOCATIONS_BY_USER, Location.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Location save(Location location) {
        entityManager.persist(location);
        return location;
    }

    public void delete(Location location) {
        entityManager.remove(location);
    }
}
