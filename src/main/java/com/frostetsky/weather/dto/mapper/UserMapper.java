package com.frostetsky.weather.dto.mapper;

import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserReadDto toUserReadDto(User user) {
        return new UserReadDto(
                user.getId(),
                user.getLogin()
        );
    }
}
