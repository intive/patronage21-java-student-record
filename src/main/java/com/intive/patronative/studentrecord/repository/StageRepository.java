package com.intive.patronative.studentrecord.repository;

import com.intive.patronative.studentrecord.repository.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, BigDecimal> {

    List<Stage> findAllByTechnologyGroup_Name(String name);

}