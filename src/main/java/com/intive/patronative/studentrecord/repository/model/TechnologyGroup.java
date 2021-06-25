package com.intive.patronative.studentrecord.repository.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "technology_group", schema = "student_record")
public class TechnologyGroup {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "technology_group_generator")
    @SequenceGenerator(name = "technology_group_generator", sequenceName = "technology_group_seq",
            schema = "student_record", allocationSize = 10)
    private BigDecimal id;

    @Column(name = "name",nullable = false, unique = true, length = 32)
    private String name;

}