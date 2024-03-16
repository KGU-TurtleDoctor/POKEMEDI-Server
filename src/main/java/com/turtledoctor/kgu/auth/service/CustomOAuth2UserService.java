package com.turtledoctor.kgu.auth.service;

import com.turtledoctor.kgu.auth.dto.CustomOAuth2User;
import com.turtledoctor.kgu.auth.dto.KakaoResponse;
import com.turtledoctor.kgu.auth.dto.OAuth2Response;
import com.turtledoctor.kgu.auth.dto.UserDTO;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        OAuth2Response oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        String username = oAuth2Response.getProviderId();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setName(oAuth2Response.getName());
        userDTO.setRole("ROLE_USER");

        return new CustomOAuth2User(userDTO);
    }
}
