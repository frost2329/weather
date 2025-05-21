package com.frostetsky.weather.dto.mapper;

import com.frostetsky.weather.db.entity.Location;
import com.frostetsky.weather.dto.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public Location toEntity(LocationDto dto, Long userId) {
        return Location.builder()
                .name(dto.name())
                .longitude(Double.parseDouble(dto.lon()))
                .latitude(Double.parseDouble(dto.lat()))
                .userId(userId)
                .build();
    }
}
