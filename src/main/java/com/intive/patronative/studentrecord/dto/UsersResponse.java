package com.intive.patronative.studentrecord.dto;

import lombok.Value;

@Value
public class UsersResponse<T> {

    T users;

}