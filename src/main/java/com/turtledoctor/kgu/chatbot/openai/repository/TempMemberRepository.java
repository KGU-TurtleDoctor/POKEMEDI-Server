package com.turtledoctor.kgu.chatbot.openai.repository;

import com.turtledoctor.kgu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempMemberRepository extends JpaRepository<Member,Long> {
    Member findByKakaoId(Long kakaoId);
}
