package com.frostetsky.weather;

import com.frostetsky.weather.config.LiquibaseTestConfig;
import com.frostetsky.weather.config.TestConfig;
import com.frostetsky.weather.dto.UserCreateDto;
import com.frostetsky.weather.dto.UserLoginDto;
import com.frostetsky.weather.exception.auth.InvalidCredentialsException;
import com.frostetsky.weather.exception.registration.PasswordMismatchException;
import com.frostetsky.weather.exception.registration.UserAlreadyExistException;
import com.frostetsky.weather.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringJUnitConfig({TestConfig.class, LiquibaseTestConfig.class})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void checkRegistration() {
        UserCreateDto userCreateDto = new UserCreateDto("testuser", "test", "test");
        UserLoginDto userLoginDto = new UserLoginDto("testuser", "test");
        userService.createUser(userCreateDto);
        String session = userService.authenticateUser(userLoginDto);
        assert session != null;
    }

    @Test
    void checkRegistrationWithDifferentPass() {
        UserCreateDto userCreateDto = new UserCreateDto("User", "test", "test1");
        Assertions.assertThrows(PasswordMismatchException.class, () -> {
            userService.createUser(userCreateDto);
        });
    }

    @Test
    void checkRegistrationWithNouUniqueLogin () {
        UserCreateDto userCreateDto = new UserCreateDto("Ren", "123", "123");
        UserCreateDto userCreateDto2 = new UserCreateDto("Ren", "123", "123");
        userService.createUser(userCreateDto);
        Assertions.assertThrows(UserAlreadyExistException.class, () -> {
            userService.createUser(userCreateDto2);
        });
    }

    @Test
    void checkLoginWithIncorrectPassword () {
        UserCreateDto userCreateDto = new UserCreateDto("Ben", "123", "123");
        UserLoginDto userLoginDto = new UserLoginDto("Ben", "1234");
        userService.createUser(userCreateDto);
        Assertions.assertThrows(InvalidCredentialsException.class, () -> {
            userService.authenticateUser(userLoginDto);
        });
    }
}
