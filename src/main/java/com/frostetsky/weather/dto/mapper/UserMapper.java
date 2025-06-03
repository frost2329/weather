package com.frostetsky.weather.dto.mapper;

import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.db.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserReadDto toDto(User user);
}
