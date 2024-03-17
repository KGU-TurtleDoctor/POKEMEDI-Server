package com.turtledoctor.kgu.repository;

import com.turtledoctor.kgu.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username); // 내부 SELECT문을 날릴 수 있음.
}
