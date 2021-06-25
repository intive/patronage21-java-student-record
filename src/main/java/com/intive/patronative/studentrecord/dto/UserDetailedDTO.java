package com.intive.patronative.studentrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@AllArgsConstructor
public class UserDetailedDTO {

    String login;
    String firstName;
    String lastName;
    byte[] image;
    String status;
    Set<UserStageDTO> stages;

}