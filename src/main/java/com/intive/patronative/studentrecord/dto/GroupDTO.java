package com.intive.patronative.studentrecord.dto;

import lombok.Value;

import java.util.Set;

@Value
public class GroupDTO {

    String name;
    Double averageGrade;
    Set<UserStageDTO> stages;

}
