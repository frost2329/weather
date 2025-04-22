package com.frostetsky.weather.service;

import com.frostetsky.weather.dto.UserCreateDto;
import com.frostetsky.weather.dto.UserLoginDto;
import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.db.entity.Session;
import com.frostetsky.weather.db.entity.User;
import com.frostetsky.weather.exception.InvalidCredentialsException;
import com.frostetsky.weather.dto.mapper.UserMapper;
import com.frostetsky.weather.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final UserMapper userMapper;

    public void createUser(UserCreateDto userDto) {
        userRepository.save(User.builder()
                .login(userDto.username())
                .password(userDto.password())
                .build());
    }

    public String login(UserLoginDto userLoginDto) {
        User user = userRepository.getUserByLogin(userLoginDto.username()).orElseThrow(InvalidCredentialsException::new);
        if(!userLoginDto.username().equals(user.getLogin()) || !userLoginDto.password().equals(user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        Session session = sessionService.createSession(user);
        return session.getId().toString();
    }

    public UserReadDto getUserIdBySession(UUID sessionId) {
        return userMapper.toUserReadDto(userRepository.findUserBySession(sessionId).orElseThrow(()-> new RuntimeException("Пользователь не найден")));
    }
}
