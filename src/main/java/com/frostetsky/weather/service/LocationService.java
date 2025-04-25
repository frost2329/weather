package com.frostetsky.weather.service;

import com.frostetsky.weather.db.entity.Location;
import com.frostetsky.weather.db.repository.LocationRepository;
import com.frostetsky.weather.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> getLocationsByUser(Long userId) {
        return locationRepository.findLocationsByUser(userId);
    }

    public Location addLocationForUser(LocationDto locationDto, Long userId) {
        Location location = Location.builder()
                .name(locationDto.name())
                .latitude(Double.parseDouble(locationDto.lat()))
                .longitude(Double.parseDouble(locationDto.lon()))
                .userId(userId)
                .build();
        locationRepository.save(location);
        return location;
    }

    public void removeLocation(Long locationId) {
        locationRepository.delete(locationId);
    }
}
