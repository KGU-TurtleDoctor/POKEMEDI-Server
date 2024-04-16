package com.turtledoctor.kgu.reply.Temp;

import com.turtledoctor.kgu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempMemberRepsitory extends JpaRepository<Member, Long> {
    Member findMemberByKakaoId(Long kakaoId);
}
