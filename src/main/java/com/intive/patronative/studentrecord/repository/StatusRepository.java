package com.intive.patronative.studentrecord.repository;

import com.intive.patronative.studentrecord.repository.model.Status;
import com.intive.patronative.studentrecord.repository.model.TechnologyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, BigDecimal> {

    Optional<Status> findByName(String name);

}