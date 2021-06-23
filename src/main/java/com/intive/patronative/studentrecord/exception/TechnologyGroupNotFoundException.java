package com.intive.patronative.studentrecord.exception;

import com.intive.patronative.studentrecord.config.LocaleConfig;
import lombok.Getter;

@Getter
public class TechnologyGroupNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = LocaleConfig.getLocaleMessage("technologyGroupNotFoundMessage");
    private static final String FIELD_NAME = "technologyGroup";

    public TechnologyGroupNotFoundException(final String fieldValue) {
        super(FIELD_NAME, fieldValue, MESSAGE);
    }

}
