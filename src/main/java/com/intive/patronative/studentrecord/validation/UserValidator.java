package com.intive.patronative.studentrecord.validation;

import com.intive.patronative.studentrecord.config.LocaleConfig;
import com.intive.patronative.studentrecord.dto.EditedUserDTO;
import com.intive.patronative.studentrecord.dto.SaveUserDTO;
import com.intive.patronative.studentrecord.exception.InvalidArgumentException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private static final int MAX_LOGIN_LENGTH_IN_DATABASE = 32;
    private static final int MAX_FIRST_NAME_LENGTH_IN_DATABASE = 64;
    private static final int MAX_LAST_NAME_LENGTH_IN_DATABASE = 64;
    private static final Set<String> ALLOWED_IMAGE_TYPES = Set.of("image/png", "image/jpeg", "image/gif");
    private static final Set<String> ALLOWED_STATUSES = Set.of("INACTIVE", "ACTIVE");

    private static final Matcher LOGIN_MATCHER = Pattern.compile("^[a-zA-Z0-9]+$").matcher("");
    private static final Matcher FIRST_NAME_MATCHER = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+").matcher("");
    private static final Matcher LAST_NAME_MATCHER = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+([- ][a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+)?$")
            .matcher("");

    private final ValidationHelper validationHelper;

    @Value("${validators.user.first-name.length.min}")
    private int minFirstNameLength;
    @Getter(AccessLevel.PACKAGE)
    @Value("${validators.user.first-name.length.max}")
    private int maxFirstNameLength;
    @Value("${validators.user.last-name.length.min}")
    private int minLastNameLength;
    @Getter(AccessLevel.PACKAGE)
    @Value("${validators.user.last-name.length.max}")
    private int maxLastNameLength;
    @Value("${validators.user.login.length.min}")
    private int minLoginLength;
    @Getter(AccessLevel.PACKAGE)
    @Value("${validators.user.login.length.max}")
    private int maxLoginLength;

    public void validateSaveData(final SaveUserDTO userRegistrationRequestDTO) {
        final var fieldErrors = getSaveUserFieldErrors(userRegistrationRequestDTO);

        if (!fieldErrors.isEmpty()) {
            throw new InvalidArgumentException(fieldErrors);
        }
    }

    public void validateUserEditionData(final EditedUserDTO editedUserDTO) throws InvalidArgumentException {
        final var fieldErrors = getUserEditionFieldErrors(editedUserDTO);

        if (!fieldErrors.isEmpty()) {
            throw new InvalidArgumentException(fieldErrors);
        }
    }

    public void validateUserImage(final MultipartFile image) {
        final var fieldError = checkImage(image);

        if (!isNull(fieldError)) {
            throw new InvalidArgumentException(List.of(fieldError));
        }
    }

    private List<FieldError> getSaveUserFieldErrors(final SaveUserDTO saveUserDTO) {
        return Optional.ofNullable(saveUserDTO)
                .map(user -> Stream
                        .of(checkLogin(saveUserDTO.getLogin(), true),
                                checkFirstName(saveUserDTO.getFirstName(), true),
                                checkLastName(saveUserDTO.getLastName(), true),
                                checkStatus(saveUserDTO.getStatus(), true))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private List<FieldError> getUserEditionFieldErrors(final EditedUserDTO editedUserDTO) {
        return Optional.ofNullable(editedUserDTO)
                .map(user -> Stream
                        .of(checkFirstName(user.getFirstName(), false),
                                checkLastName(user.getLastName(), false))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private FieldError checkLogin(final String login, final boolean isRequired) {
        final var localeMessage = LocaleConfig.getLocaleMessage("validationLoginMessage");
        final var charactersMessage = validationHelper.getMinMaxCharactersMessage(minLoginLength, maxLoginLength);
        final var message = validationHelper.getFormattedMessage(localeMessage, charactersMessage);

        return (isRequired || login != null) && !(validationHelper.checkLength(login, minLoginLength, maxLoginLength) && isLoginValid(login))
                ? validationHelper.getFieldError("login", login, message)
                : null;
    }

    public boolean isLoginValid(final String login) {
        return nonNull(login) && (login.length() <= MAX_LOGIN_LENGTH_IN_DATABASE) && LOGIN_MATCHER.reset(login).matches();
    }

    private FieldError checkFirstName(final String firstName, final boolean isRequired) {
        final var localeMessage = LocaleConfig.getLocaleMessage("validationFirstNameMessage");
        final var charactersMessage = validationHelper.getMinMaxCharactersMessage(minFirstNameLength, maxFirstNameLength);
        final var message = validationHelper.getFormattedMessage(localeMessage, charactersMessage);

        return (isRequired || firstName != null) && !(validationHelper.checkLength(firstName, minFirstNameLength, maxFirstNameLength) && isFirstNameValid(firstName))
                ? validationHelper.getFieldError("firstName", firstName, message)
                : null;
    }

    public boolean isFirstNameValid(final String firstName) {
        return (firstName != null) && (firstName.length() <= MAX_FIRST_NAME_LENGTH_IN_DATABASE)
                && FIRST_NAME_MATCHER.reset(firstName).matches();
    }

    private FieldError checkLastName(final String lastName, final boolean isRequired) {
        final var localeMessage = LocaleConfig.getLocaleMessage("validationLastNameMessage");
        final var charactersMessage = validationHelper.getMinMaxCharactersMessage(minLastNameLength, maxLastNameLength);
        final var message = validationHelper.getFormattedMessage(localeMessage, charactersMessage);

        return (isRequired || lastName != null) && !(validationHelper.checkLength(lastName, minLastNameLength, maxLastNameLength) && isLastNameValid(lastName))
                ? validationHelper.getFieldError("lastName", lastName, message)
                : null;
    }

    public boolean isLastNameValid(final String lastName) {
        return (lastName != null) && (lastName.length() <= MAX_LAST_NAME_LENGTH_IN_DATABASE)
                && LAST_NAME_MATCHER.reset(lastName).matches();
    }

    private FieldError checkImage(final MultipartFile image) {
        final var localeImageFormatMessage = LocaleConfig.getLocaleMessage("validationImageFormatMessage");
        final var imageFormatMessage = validationHelper.getFormattedMessage(localeImageFormatMessage, ALLOWED_IMAGE_TYPES);
        final var imageNotFoundMessage = LocaleConfig.getLocaleMessage("validationImageNotSentMessage");

        if (isNull(image) || image.isEmpty()) {
            return validationHelper.getFieldError("image", null, imageNotFoundMessage);
        }

        return isImageValid(image)
                ? null
                : validationHelper.getFieldError("image", image.getContentType(), imageFormatMessage);
    }

    public static boolean isImageValid(final MultipartFile image) {
        return nonNull(image) && ALLOWED_IMAGE_TYPES.contains(image.getContentType());
    }

    private FieldError checkStatus(final String status, final boolean isRequired) {
        final var localeStatusFormatMessage = LocaleConfig.getLocaleMessage("validationStatusFormatMessage");
        final var statusFormatMessage = validationHelper.getFormattedMessage(localeStatusFormatMessage, ALLOWED_STATUSES);

        return isStatusValid(status)
                ? null
                : validationHelper.getFieldError("status", status, statusFormatMessage);
    }

    public static boolean isStatusValid(final String status) {
        return nonNull(status) && ALLOWED_STATUSES.contains(status);
    }

}