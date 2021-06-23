package com.intive.patronative.studentrecord.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class SaveUserDTO {

    String login;
    String firstName;
    String lastName;
    String status;
    Set<String> groups;

}
