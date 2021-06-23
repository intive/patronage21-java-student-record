package com.intive.patronative.studentrecord.repository;

import com.intive.patronative.studentrecord.repository.model.UserStage;
import com.intive.patronative.studentrecord.repository.model.UserStageKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStageRepository extends JpaRepository<UserStage, UserStageKey> {
}