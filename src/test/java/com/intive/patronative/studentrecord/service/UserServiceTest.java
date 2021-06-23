package com.intive.patronative.studentrecord.service;

import com.intive.patronative.studentrecord.exception.InvalidArgumentException;
import com.intive.patronative.studentrecord.exception.UserNotFoundException;
import com.intive.patronative.studentrecord.mapper.UserMapper;
import com.intive.patronative.studentrecord.repository.UserRepository;
import com.intive.patronative.studentrecord.repository.model.User;
import com.intive.patronative.studentrecord.repository.model.UserStage;
import com.intive.patronative.studentrecord.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserValidator userValidator;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @ParameterizedTest
    @MethodSource("getUsers_shouldNotThrow_data")
    void getUsers_shouldNotThrow(final List<User> users) {
        Mockito.when(userRepository.findAll()).thenReturn(users);

        assertDoesNotThrow(() -> userService.getUsers());
    }

    private static Stream<Arguments> getUsers_shouldNotThrow_data() {
        return Stream.of(
                Arguments.of(Collections.emptyList()),
                Arguments.of(Collections.emptyList()),
                Arguments.of(Collections.singletonList(null)),
                Arguments.of(Collections.singletonList(new User())),
                Arguments.of(Collections.singletonList(setUserUp(Collections.singleton(null)))),
                Arguments.of(Collections.singletonList(setUserUp(Collections.singleton(null)))),
                Arguments.of(Collections.singletonList(setUserUp(Collections.singleton(new UserStage()))))
        );
    }

    @ParameterizedTest
    @MethodSource("getUser_shouldNotThrow_data")
    void getUser_shouldNotThrow(final User user) {
        Mockito.when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user));
        Mockito.when(userValidator.isLoginValid(anyString())).thenReturn(true);

        assertDoesNotThrow(() -> userService.getUser(anyString()));
    }

    private static Stream<Arguments> getUser_shouldNotThrow_data() {
        return Stream.of(
                Arguments.of(new User()),
                Arguments.of(setUserUp(Collections.emptySet())),
                Arguments.of(setUserUp(Collections.singleton(null))),
                Arguments.of(setUserUp(Collections.singleton(new UserStage())))
        );
    }

    @Test
    void getUser_shouldThrowInvalidArgumentException() {
        Mockito.when(userValidator.isLoginValid(anyString())).thenReturn(false);

        assertThrows(InvalidArgumentException.class, () -> userService.getUser(anyString()));
    }

    @Test
    void getUser_shouldThrowUserNotFoundException() {
        Mockito.when(userRepository.findByLogin(anyString())).thenReturn(Optional.empty());
        Mockito.when(userValidator.isLoginValid(anyString())).thenReturn(true);

        assertThrows(UserNotFoundException.class, () -> userService.getUser(anyString()));
    }

    private static User setUserUp(final Set<UserStage> userStages) {
        final var user = new User();

        user.setUserStages(userStages);

        return user;
    }

}