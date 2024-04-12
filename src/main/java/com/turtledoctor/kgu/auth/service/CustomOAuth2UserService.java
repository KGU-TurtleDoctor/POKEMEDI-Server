package com.turtledoctor.kgu.auth.service;

import com.turtledoctor.kgu.auth.dto.CustomOAuth2User;
import com.turtledoctor.kgu.auth.dto.KakaoResponse;
import com.turtledoctor.kgu.auth.dto.OAuth2Response;
import com.turtledoctor.kgu.auth.dto.UserDTO;
import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.enums.UserRole;
import com.turtledoctor.kgu.entity.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        OAuth2Response oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        Long kakaoId = oAuth2Response.getProviderId();
        Member existData = memberRepository.findBykakaoId(kakaoId);

        if(existData == null) { // 첫 로그인

            Member member = Member.builder().build();
            member.setKakaoId(kakaoId);
            member.setEmail(oAuth2Response.getEmail());
            member.setName(oAuth2Response.getName());
            member.setRole(UserRole.NORMAL);

            memberRepository.save(member);

            UserDTO userDTO = new UserDTO();
            userDTO.setKakaoId(kakaoId);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(String.valueOf(UserRole.NORMAL));
            userDTO.setEmail(oAuth2Response.getEmail());

            return new CustomOAuth2User(userDTO);

        } else { // 다시 로그인

            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            memberRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setKakaoId(existData.getKakaoId());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(String.valueOf(existData.getRole()));
            userDTO.setEmail(oAuth2Response.getEmail());

            return new CustomOAuth2User(userDTO);
        }
    }
}
