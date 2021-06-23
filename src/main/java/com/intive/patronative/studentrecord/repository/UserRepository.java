package com.intive.patronative.studentrecord.repository;

import com.intive.patronative.studentrecord.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, BigDecimal> {

    Optional<User> findByLogin(final String login);
    boolean existsByLogin(String login);

}