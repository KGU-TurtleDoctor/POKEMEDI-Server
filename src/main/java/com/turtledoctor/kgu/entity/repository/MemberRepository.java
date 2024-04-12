package com.turtledoctor.kgu.entity.repository;

import com.turtledoctor.kgu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findBykakaoId(Long kakaoId); // 내부 SELECT문을 날릴 수 있음.
}
