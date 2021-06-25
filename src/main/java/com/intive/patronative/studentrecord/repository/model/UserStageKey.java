package com.intive.patronative.studentrecord.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Embeddable
@NoArgsConstructor
public class UserStageKey implements Serializable {

    @Column(name = "user_id", nullable = false)
    private BigDecimal userId;

    @Column(name = "stage_id", nullable = false)
    private BigDecimal stageId;

}