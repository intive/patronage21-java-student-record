package com.intive.patronative.studentrecord.dto;

import lombok.Value;

@Value
public class UserRequest<T> {

    T user;

}
