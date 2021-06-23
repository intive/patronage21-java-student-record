package com.intive.patronative.studentrecord.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {ValidationHelper.class, UserValidator.class})
class UserValidatorTest {

    @Autowired
    private UserValidator userValidator;

    @ParameterizedTest
    @MethodSource("isLoginValid_shouldNotThrow_data")
    void isLoginValid_shouldNotThrow(final String login) {
        assertDoesNotThrow(() -> userValidator.isLoginValid(login));
    }

    private static Stream<String> isLoginValid_shouldNotThrow_data() {
        return Stream.of(null, "", "adad*adasd-");
    }

    @ParameterizedTest
    @MethodSource("isLoginValid_shouldReturnTrue_data")
    void isLoginValid_shouldReturnTrue(final String login) {
        assertTrue(userValidator.isLoginValid(login));
    }

    private static Stream<String> isLoginValid_shouldReturnTrue_data() {
        return Stream.of( "adadadas", "AnnaNowak", "Jan");
    }

    @ParameterizedTest
    @MethodSource("isLoginValid_shouldReturnFalse_data")
    void isLoginValid_shouldReturnFalse(final String login) {
        assertFalse(userValidator.isLoginValid(login));
    }

    private static Stream<String> isLoginValid_shouldReturnFalse_data() {
        return Stream.of( " ", "adadad*as", "Ann-aNowak", " Jan");
    }

}