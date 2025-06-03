package com.frostetsky.weather.dto.mapper;

import com.frostetsky.weather.db.entity.Location;
import com.frostetsky.weather.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "latitude", expression = "java(Double.parseDouble(dto.lat()))")
    @Mapping(target = "longitude", expression = "java(Double.parseDouble(dto.lon()))")
    @Mapping(target = "userId", source = "userId")
    Location toEntity(LocationDto dto, Long userId);
}
