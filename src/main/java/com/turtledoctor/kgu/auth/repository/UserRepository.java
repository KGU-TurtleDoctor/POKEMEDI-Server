package com.turtledoctor.kgu.auth.repository;

import com.turtledoctor.kgu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {
    Member findBykakaoId(String kakaoId); // 내부 SELECT문을 날릴 수 있음.
}
