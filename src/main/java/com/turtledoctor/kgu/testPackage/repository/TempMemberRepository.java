package com.turtledoctor.kgu.testPackage.repository;

import com.turtledoctor.kgu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempMemberRepository extends JpaRepository<Member,Long> {
    Member findByKakaoId(String kakaoId);

    List<Member> findAllByName(String nickname);
}