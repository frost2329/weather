package com.frostetsky.weather.dto.mapper;

import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.db.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationDto toLocationDto(Location location) {
        return new LocationDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude());
    }
}
