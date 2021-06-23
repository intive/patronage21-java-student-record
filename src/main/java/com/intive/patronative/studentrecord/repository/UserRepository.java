package com.intive.patronative.studentrecord.repository;

import com.intive.patronative.studentrecord.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends JpaRepository<User, BigDecimal> {
}