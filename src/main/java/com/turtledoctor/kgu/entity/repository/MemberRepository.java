package com.turtledoctor.kgu.entity.repository;

import com.turtledoctor.kgu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findBykakaoId(String kakaoId); // 내부 SELECT문을 날릴 수 있음.
}
