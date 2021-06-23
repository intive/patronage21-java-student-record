package com.intive.patronative.studentrecord.dto;

import lombok.Value;

@Value
public class UserResponse<T> {

    T user;

}