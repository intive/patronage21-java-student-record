package com.intive.patronative.studentrecord.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

@Component
public class UserValidator {

    private static final int MAX_LOGIN_LENGTH_IN_DATABASE = 32;
    private static final Matcher LOGIN_MATCHER = Pattern.compile("^[a-zA-Z0-9]+$").matcher("");

    public boolean isLoginValid(final String login) {
        return nonNull(login) && (login.length() <= MAX_LOGIN_LENGTH_IN_DATABASE) && LOGIN_MATCHER.reset(login).matches();
    }

}