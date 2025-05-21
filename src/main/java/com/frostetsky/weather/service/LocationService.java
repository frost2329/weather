package com.frostetsky.weather.service;

import com.frostetsky.weather.db.entity.Location;
import com.frostetsky.weather.db.repository.LocationRepository;
import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.mapper.LocationMapper;
import com.frostetsky.weather.exception.LocationServiceException;
import com.frostetsky.weather.exception.registration.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional(readOnly = true)
    public List<Location> getLocationsByUser(Long userId) {
        return locationRepository.findLocationsByUser(userId);
    }

    @Transactional
    public Location addLocationForUser(LocationDto locationDto, Long userId) {
        try {
            return locationRepository.save(locationMapper.toEntity(locationDto, userId));
        } catch (ConstraintViolationException e) {
            throw new LocationServiceException("Ошибка при сохранении локации. Локация %s уже добавлена"
                    .formatted(locationDto.name()), e);
        } catch (Exception e) {
            throw new LocationServiceException("Ошибка при сохранении локации", e);
        }
    }

    @Transactional
    public void removeLocation(Long locationId, Long userId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationServiceException("Локация с ID %s не найдена".formatted(locationId)));
        if (!location.getUserId().equals(userId)) {
            throw new LocationServiceException("Удаление локации не возможно, локация вам не принадлежит");
        }
        locationRepository.delete(location);
    }
}
