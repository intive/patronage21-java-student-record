package com.intive.patronative.studentrecord.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class SaveUserResponse {

    String login;
    String firstName;
    String lastName;
    String status;
    byte[] image;
    Set<GroupDTO> groups;

}
