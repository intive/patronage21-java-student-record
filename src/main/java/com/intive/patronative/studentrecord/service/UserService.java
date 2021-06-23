package com.intive.patronative.studentrecord.service;

import com.intive.patronative.studentrecord.dto.UserDTO;
import com.intive.patronative.studentrecord.dto.UserDetailedDTO;
import com.intive.patronative.studentrecord.dto.UserResponse;
import com.intive.patronative.studentrecord.dto.UsersResponse;
import com.intive.patronative.studentrecord.exception.InvalidArgumentException;
import com.intive.patronative.studentrecord.exception.UserNotFoundException;
import com.intive.patronative.studentrecord.mapper.UserMapper;
import com.intive.patronative.studentrecord.repository.UserRepository;
import com.intive.patronative.studentrecord.repository.model.User;
import com.intive.patronative.studentrecord.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    public UsersResponse<Set<UserDTO>> getUsers() {
        final var users = userRepository.findAll();
        final var usersResponse = users.stream()
                .map(userMapper::mapToUserDTO)
                .collect(Collectors.toSet());

        return new UsersResponse<>(usersResponse);
    }

    public UserResponse<UserDetailedDTO> getUser(final String login) {
        final var user = getUserFromDatabaseByLogin(login);
        final var userResponse = userMapper.mapToUserDetailedDTO(user);

        return new UserResponse<>(userResponse);
    }

    private User getUserFromDatabaseByLogin(final String login) {
        if (!userValidator.isLoginValid(login)) {
            throw new InvalidArgumentException("login", login);
        }

        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

}