package com.intive.patronative.studentrecord.mapper;

import com.intive.patronative.studentrecord.repository.model.Stage;
import com.intive.patronative.studentrecord.repository.model.User;
import com.intive.patronative.studentrecord.repository.model.UserStage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @ParameterizedTest
    @MethodSource("mapToUserDTO_shouldNotThrow_data")
    void mapToUserDTO_shouldNotThrow(final User user) {
        assertDoesNotThrow(() -> userMapper.mapToUserDTO(user));
    }

    private static Stream<User> mapToUserDTO_shouldNotThrow_data() {
        return Stream.of(
                null,
                new User(),
                setUserUp(null),
                setUserUp(Collections.emptySet()),
                setUserUp(Collections.singleton(null)),
                setUserUp(Collections.singleton(new UserStage())),
                setUserUp(Collections.singleton(setUserStageUp(null, null))),
                setUserUp(Collections.singleton(setUserStageUp(null, new Stage()))),
                setUserUp(Collections.singleton(setUserStageUp(new User(), null))),
                setUserUp(Collections.singleton(setUserStageUp(new User(), new Stage())))
        );
    }

    @ParameterizedTest
    @MethodSource("mapToUserDetailedDTO_shouldNotThrow_data")
    void mapToUserDetailedDTO_shouldNotThrow(final User user) {
        assertDoesNotThrow(() -> userMapper.mapToUserDetailedDTO(user));
    }

    private static Stream<User> mapToUserDetailedDTO_shouldNotThrow_data() {
        return Stream.of(
                null,
                new User(),
                setUserUp(null),
                setUserUp(Collections.emptySet()),
                setUserUp(Collections.singleton(null)),
                setUserUp(Collections.singleton(new UserStage())),
                setUserUp(Collections.singleton(setUserStageUp(null, null))),
                setUserUp(Collections.singleton(setUserStageUp(null, new Stage()))),
                setUserUp(Collections.singleton(setUserStageUp(new User(), null))),
                setUserUp(Collections.singleton(setUserStageUp(new User(), new Stage())))
        );
    }

    private static User setUserUp(final Set<UserStage> userStages) {
        final var user = new User();

        user.setUserStages(userStages);

        return user;
    }

    private static UserStage setUserStageUp(final User user, final Stage stage) {
        final var userStage = new UserStage();

        userStage.setUser(user);
        userStage.setStage(stage);

        return userStage;
    }

}