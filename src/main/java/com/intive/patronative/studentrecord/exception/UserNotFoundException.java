package com.intive.patronative.studentrecord.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends EntityNotFoundException{

    private static final String MESSAGE = "User not found";
    private static final String FIELD_NAME = "login";

    public UserNotFoundException(final String fieldValue) {
        super(FIELD_NAME, fieldValue, MESSAGE);
    }

}