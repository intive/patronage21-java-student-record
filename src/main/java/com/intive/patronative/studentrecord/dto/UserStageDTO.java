package com.intive.patronative.studentrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class UserStageDTO {

    String name;
    String reason;
    Double mark;
    String group;
    Integer year;

}