package com.intive.patronative.studentrecord.mapper;

import com.intive.patronative.studentrecord.dto.GroupDTO;
import com.intive.patronative.studentrecord.dto.SaveUserDTO;
import com.intive.patronative.studentrecord.dto.SaveUserResponse;
import com.intive.patronative.studentrecord.dto.UserDTO;
import com.intive.patronative.studentrecord.dto.UserDetailedDTO;
import com.intive.patronative.studentrecord.dto.UserStageDTO;
import com.intive.patronative.studentrecord.repository.model.Stage;
import com.intive.patronative.studentrecord.repository.model.Status;
import com.intive.patronative.studentrecord.repository.model.TechnologyGroup;
import com.intive.patronative.studentrecord.repository.model.User;
import com.intive.patronative.studentrecord.repository.model.UserStage;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class UserMapper {

    public UserDetailedDTO mapToUserDetailedDTO(final User user) {
        return Optional.ofNullable(user)
                .map(item -> UserDetailedDTO.builder()
                        .login(user.getLogin())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .image(user.getImage())
                        .status(Optional.ofNullable(user.getStatus()).map(Status::getName).orElse(null))
                        .stages(Optional.ofNullable(user.getUserStages())
                                .map(userStages -> userStages.stream()
                                        .map(this::mapToUserStageDTO)
                                        .collect(Collectors.toSet()))
                                .orElseGet(Collections::emptySet))
                        .build())
                .orElse(null);
    }

    public SaveUserResponse mapToSaveUserResponse(final User user) {
        final var technologyGroups = Optional.ofNullable(user)
                .map(User::getUserStages)
                .stream()
                .flatMap(Collection::stream)
                .filter(userStage -> nonNull(userStage.getStage()))
                .map(userStage -> userStage.getStage().getTechnologyGroup())
                .collect(Collectors.toSet());

        return Optional.ofNullable(user)
                .map(item -> SaveUserResponse.builder()
                        .login(item.getLogin())
                        .firstName(item.getFirstName())
                        .lastName(item.getLastName())
                        .image(item.getImage())
                        .status(Optional.ofNullable(item.getStatus()).map(Status::getName).orElse(null))
                        .groups(technologyGroups.stream()
                                .map(group -> mapToGroupDTO(group, item.getUserStages()))
                                .collect(Collectors.toSet())
                        ).build())
                .orElse(null);
    }

    public User mapToUser(final SaveUserDTO saveUserDTO, final Status userStatus) {
        return Optional.ofNullable(saveUserDTO)
                .map(item -> User.builder()
                        .login(saveUserDTO.getLogin())
                        .firstName(saveUserDTO.getFirstName())
                        .lastName(saveUserDTO.getLastName())
                        .status(userStatus)
                        .build())
                .orElse(null);
    }

    private GroupDTO mapToGroupDTO(final TechnologyGroup group, final Set<UserStage> userStages) {
        final var userGroupStages = Optional.ofNullable(userStages)
                .map(stages -> stages.stream()
                        .filter(userStage -> nonNull(userStage.getStage()))
                        .filter(userStage -> nonNull(userStage.getStage().getTechnologyGroup()))
                        .filter(userStage -> userStage.getStage().getTechnologyGroup().equals(group))
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        return Optional.ofNullable(group)
                .map(item -> new GroupDTO(group.getName(), countAverageGrade(userGroupStages), userGroupStages.stream()
                        .map(this::mapToUserStageDTO).collect(Collectors.toSet())))
                .orElse(null);
    }


    private UserStageDTO mapToUserStageDTO(final UserStage userStage) {
        return Optional.ofNullable(userStage)
                .map(item -> UserStageDTO.builder()
                        .name(Optional.ofNullable(item.getStage()).map(Stage::getName).orElse(null))
                        .reason(item.getReason())
                        .mark(item.getMark())
                        .group(getTechGroupName(userStage))
                        .year(Optional.ofNullable(userStage.getStage()).map(Stage::getYear).orElse(null))
                        .build())
                .orElse(null);
    }

    public UserDTO mapToUserDTO(final User user) {
        return Optional.ofNullable(user)
                .map(item -> UserDTO.builder()
                        .login(user.getLogin())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .image(user.getImage())
                        .status(Optional.ofNullable(user.getStatus()).map(Status::getName).orElse(null))
                        .techGroups(getTechGroups(user.getUserStages()))
                        .build())
                .orElse(null);
    }

    private Set<String> getTechGroups(final Set<UserStage> userStages) {
        return Optional.ofNullable(userStages)
                .map(items -> items.stream()
                        .map(this::getTechGroupName)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()))
                .orElse(null);
    }

    private String getTechGroupName(final UserStage userStage) {
        if (nonNull(userStage) && nonNull(userStage.getStage()) && nonNull(userStage.getStage().getTechnologyGroup())) {
            return userStage.getStage().getTechnologyGroup().getName();
        }

        return null;
    }

    private Double countAverageGrade(final Set<UserStage> userStages) {
        if (nonNull(userStages)) {
            return userStages.stream()
                    .filter(userStage -> nonNull(userStage) && nonNull(userStage.getMark()))
                    .mapToDouble(UserStage::getMark)
                    .average()
                    .orElse(0.0);
        }
        return null;
    }

}