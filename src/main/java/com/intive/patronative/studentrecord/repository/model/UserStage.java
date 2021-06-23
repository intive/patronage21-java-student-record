package com.intive.patronative.studentrecord.repository.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_stage", schema = "student_record")
public class UserStage {

    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private UserStageKey id;

    @Column(name = "mark")
    private Double mark;

    @Column(name = "reason", length = 512)
    private String reason;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("stageId")
    @JoinColumn(name = "stage_id")
    private Stage stage;

}