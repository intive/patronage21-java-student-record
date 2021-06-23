package com.intive.patronative.studentrecord.service;

import com.intive.patronative.studentrecord.dto.EditedUserDTO;
import com.intive.patronative.studentrecord.dto.SaveUserDTO;
import com.intive.patronative.studentrecord.dto.SaveUserResponse;
import com.intive.patronative.studentrecord.dto.UserDTO;
import com.intive.patronative.studentrecord.dto.UserDetailedDTO;
import com.intive.patronative.studentrecord.dto.UserResponse;
import com.intive.patronative.studentrecord.dto.UsersResponse;
import com.intive.patronative.studentrecord.exception.AlreadyExistsException;
import com.intive.patronative.studentrecord.exception.InvalidArgumentException;
import com.intive.patronative.studentrecord.exception.TechnologyGroupNotFoundException;
import com.intive.patronative.studentrecord.exception.UserNotFoundException;
import com.intive.patronative.studentrecord.mapper.UserMapper;
import com.intive.patronative.studentrecord.repository.StageRepository;
import com.intive.patronative.studentrecord.repository.StatusRepository;
import com.intive.patronative.studentrecord.repository.TechnologyGroupRepository;
import com.intive.patronative.studentrecord.repository.UserRepository;
import com.intive.patronative.studentrecord.repository.UserStageRepository;
import com.intive.patronative.studentrecord.repository.model.Stage;
import com.intive.patronative.studentrecord.repository.model.TechnologyGroup;
import com.intive.patronative.studentrecord.repository.model.User;
import com.intive.patronative.studentrecord.repository.model.UserStage;
import com.intive.patronative.studentrecord.repository.model.UserStageKey;
import com.intive.patronative.studentrecord.validation.TechnologyGroupsValidator;
import com.intive.patronative.studentrecord.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final TechnologyGroupsValidator technologyGroupsValidator;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final TechnologyGroupRepository groupRepository;
    private final StageRepository stageRepository;
    private final UserStageRepository userStageRepository;

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

    public void deactivateUserByLogin(final String login) {
        final var user = getUserFromDatabaseByLogin(login);

        if (isUserActive(user)) {
            user.setStatus(statusRepository.findByName("INACTIVE").orElseGet(user::getStatus));
            storeUserInDatabase(user);
        }
    }

    public void deleteImage(final String login) {
        final var user = getUserFromDatabaseByLogin(login);

        if (nonNull(user.getImage())) {
            user.setImage(null);
            storeUserInDatabase(user);
        }
    }

    public void uploadImage(final String login, final MultipartFile image) {
        userValidator.validateUserImage(image);
        final var user = getUserFromDatabaseByLogin(login);
        final byte[] profileImage = convertImageToBytes(image);
        user.setImage(profileImage);
        storeUserInDatabase(user);
    }

    public UserResponse<SaveUserResponse> saveUser(final SaveUserDTO saveUserDTO) {
        userValidator.validateSaveData(saveUserDTO);

        technologyGroupsValidator.checkTechnologyGroups(saveUserDTO.getGroups());
        if (userRepository.existsByLogin(saveUserDTO.getLogin())) {
            throw new AlreadyExistsException("login", saveUserDTO.getLogin());
        }

        final var userToSave = saveNewUserInDatabase(saveUserDTO);
        return new UserResponse<>(userMapper.mapToSaveUserResponse(userToSave));
    }

    public void updateUser(final EditedUserDTO editedUserDTO, String login) {
        userValidator.validateUserEditionData(editedUserDTO);
        final var user = getUserFromDatabaseByLogin(login);

        if (nonNull(editedUserDTO)) {
            user.setFirstName(Optional.ofNullable(editedUserDTO.getFirstName()).orElse(user.getFirstName()));
            user.setLastName(Optional.ofNullable(editedUserDTO.getLastName()).orElse(user.getLastName()));
            storeUserInDatabase(user);
        }
    }

    private byte[] convertImageToBytes(final MultipartFile image) {
        try {
            return image.getBytes();
        } catch (final IOException e) {
            throw new InvalidArgumentException(List.of(new FieldError("String", "image", image.getContentType(), false, null, null, e.getMessage())));
        }
    }

    private boolean isUserActive(final User user) {
        return nonNull(user)
                && nonNull(user.getStatus())
                && nonNull(user.getStatus().getName())
                && "ACTIVE".equals(user.getStatus().getName());
    }

    @Transactional
    public User storeUserInDatabase(final User entityUser) {
        return Optional.ofNullable(entityUser)
                .map(userRepository::save)
                .orElse(null);
    }

    private User getUserFromDatabaseByLogin(final String login) {
        if (!userValidator.isLoginValid(login)) {
            throw new InvalidArgumentException("login", login);
        }

        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    @Transactional
    private User saveNewUserInDatabase(final SaveUserDTO saveUserDTO) {
        final var userGroups = getGroupsByUser(saveUserDTO);
        final var groupStages = getStagesByGroups(userGroups);
        final var user = createUser(saveUserDTO);
        userRepository.save(user);

        final var userGroupStages = createUserStages(user, groupStages);
        userStageRepository.saveAll(userGroupStages);
        user.setUserStages(userGroupStages);

        return user;
    }

    private User createUser(final SaveUserDTO saveUserDTO) {
        final var userStatus = statusRepository.findByName(saveUserDTO.getStatus()).orElse(null);
        return userMapper.mapToUser(saveUserDTO, userStatus);
    }

    private Set<UserStage> createUserStages(final User user, List<Stage> stages) {
        return Optional.ofNullable(stages).stream()
                .flatMap(Collection::stream)
                .map(stage -> UserStage.builder()
                        .id(new UserStageKey(user.getId(), stage.getId()))
                        .stage(stage)
                        .user(user)
                        .mark(0.0)
                        .build())
                .collect(Collectors.toSet());
    }

    private Set<TechnologyGroup> getGroupsByUser(final SaveUserDTO saveUserDTO) {
        return Optional.ofNullable(saveUserDTO.getGroups())
                .map(groups -> groups.stream()
                        .filter(Objects::nonNull)
                        .map(group -> groupRepository.findByName(group)
                                .orElseThrow(() -> new TechnologyGroupNotFoundException(group)))
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    private List<Stage> getStagesByGroups(final Set<TechnologyGroup> groups) {
        return Optional.ofNullable(groups).stream()
                .flatMap(Collection::stream)
                .flatMap(group -> stageRepository.findAllByTechnologyGroup_Name(group.getName()).stream())
                .collect(Collectors.toList());
    }

}