package com.turtledoctor.kgu.comment.Temp;

import com.turtledoctor.kgu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempMemberRepository extends JpaRepository<Member, Long> {
    public Member findByKakaoId(Long kakaoId);
}
