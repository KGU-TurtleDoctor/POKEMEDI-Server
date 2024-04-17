package com.turtledoctor.kgu.testPackage.service;

import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.enums.UserRole;
import com.turtledoctor.kgu.testPackage.repository.TempMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class TempMemberService {

    private final TempMemberRepository tempMemberRepository;

    public Long createTempMember(Long kakaoId){
        tempMemberRepository.save(Member.builder()
                .kakaoId(kakaoId.toString())
                .chatHistoryList(new ArrayList<>())
                .commentList(new ArrayList<>())
                .replyList(new ArrayList<>())
                .role(UserRole.NORMAL)
                .name("김태완")
                .postList(new ArrayList<>())
                .email("thkim@naver.com").build()
        );

        return kakaoId;
    }

    public Member findTempMember(Long kakaoId){
        return tempMemberRepository.findByKakaoId(kakaoId);
    }
}