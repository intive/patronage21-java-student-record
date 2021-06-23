package com.intive.patronative.studentrecord.repository.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "stage", schema = "student_record")
@EqualsAndHashCode(exclude = {"id", "userStages"})
public class Stage {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stage_generator")
    @SequenceGenerator(name = "stage_generator", sequenceName = "stage_seq", schema = "student_record", allocationSize = 10)
    private BigDecimal id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "year", nullable = false)
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "technology_group_id", nullable = false)
    private TechnologyGroup technologyGroup;

    @OneToMany(mappedBy = "stage")
    @ToString.Exclude
    private Set<UserStage> userStages;

}