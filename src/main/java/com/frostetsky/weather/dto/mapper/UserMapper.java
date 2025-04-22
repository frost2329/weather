package com.frostetsky.weather.dto.mapper;

import com.frostetsky.weather.dto.LocationDto;
import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final LocationMapper locationMapper;

    public UserReadDto toUserReadDto(User user) {
        return new UserReadDto(
                user.getId(),
                user.getLogin()
        );
    }
}
