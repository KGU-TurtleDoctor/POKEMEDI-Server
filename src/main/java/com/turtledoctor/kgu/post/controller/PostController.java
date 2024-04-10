package com.turtledoctor.kgu.post.controller;

import com.turtledoctor.kgu.post.service.PostService;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;

    @GetMapping("/create")
    public ResponseEntity<ResponseDTO> createPost(@RequestParam(name = "kakaoId") Long kakaoId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("header1", "header");

        ResponseDTO responseDTO = ResponseDTO
                .builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.createPost(kakaoId))
                .build();
        return ResponseEntity.ok().headers(headers).body(responseDTO);
    }
}
