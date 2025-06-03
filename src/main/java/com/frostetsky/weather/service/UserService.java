package com.frostetsky.weather.service;

import com.frostetsky.weather.dto.UserCreateDto;
import com.frostetsky.weather.dto.UserLoginDto;
import com.frostetsky.weather.dto.UserReadDto;
import com.frostetsky.weather.db.entity.Session;
import com.frostetsky.weather.db.entity.User;
import com.frostetsky.weather.exception.*;
import com.frostetsky.weather.dto.mapper.UserMapper;
import com.frostetsky.weather.db.repository.UserRepository;
import com.frostetsky.weather.exception.auth.AuthenticationUserException;
import com.frostetsky.weather.exception.auth.InvalidCredentialsException;
import com.frostetsky.weather.exception.registration.CreateUserDatabaseException;
import com.frostetsky.weather.exception.registration.CreateUserException;
import com.frostetsky.weather.exception.registration.PasswordMismatchException;
import com.frostetsky.weather.exception.registration.UserAlreadyExistException;
import com.frostetsky.weather.util.PasswordUtil;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final UserMapper userMapper;

    @Transactional
    public void createUser(UserCreateDto userDto) {
        if (!userDto.password().equals(userDto.repeatPassword())) {
            throw new PasswordMismatchException();
        }
        try {
            User user = userRepository.save(User.builder()
                    .login(userDto.username())
                    .password(PasswordUtil.hashPassword(userDto.password()))
                    .build());
            if (user.getId() == null) {
                throw new CreateUserDatabaseException();
            }
        } catch (ConstraintViolationException e) {
            throw new UserAlreadyExistException(userDto.username(), e);
        } catch (PersistenceException e) {
            throw new CreateUserDatabaseException(e);
        } catch (Exception e) {
            throw new CreateUserException(e);
        }
    }

    @Transactional
    public String authenticateUser(UserLoginDto userLoginDto) {
        try {
            User user = userRepository.findByLogin(userLoginDto.username())
                    .orElseThrow(() -> new InvalidCredentialsException("Некорректное имя пользователя"));
            if (!PasswordUtil.checkPassword(userLoginDto.password(), user.getPassword())) {
                throw new InvalidCredentialsException();
            }
            Session session = sessionService.createSession(user);
            return session.getId().toString();
        } catch (InvalidCredentialsException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthenticationUserException(e);
        }
    }

    @Transactional(readOnly = true)
    public UserReadDto getUserIdBySession(UUID sessionId) {
        Session session = sessionService.getSessionById(sessionId).orElseThrow(SessionNotFoundException::new);
        User user = userRepository.findById(session.getUserId()).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }
}
