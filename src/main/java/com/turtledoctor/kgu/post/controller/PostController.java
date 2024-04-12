package com.turtledoctor.kgu.post.controller;

import com.turtledoctor.kgu.post.DTO.CreatePostRequestDTO;
import com.turtledoctor.kgu.post.DTO.PostListDTO;
import com.turtledoctor.kgu.post.DTO.PostSearchRequestDTO;
import com.turtledoctor.kgu.post.DTO.UpdatePostRequestDTO;
import com.turtledoctor.kgu.post.service.PostService;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createPost(@RequestBody CreatePostRequestDTO createPostRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("header1", "header");

        ResponseDTO responseDTO = ResponseDTO
                .builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.createPost(createPostRequestDTO))
                .build();
        return ResponseEntity.ok().headers(headers).body(responseDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updatePost(@RequestBody UpdatePostRequestDTO updatePostRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("header1", "header");

        ResponseDTO responseDTO = ResponseDTO
                .builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.updatePost(updatePostRequestDTO))
                .build();
        return ResponseEntity.ok().headers(headers).body(responseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getPostList() {
        List<PostListDTO> rawPostList = postService.createPostListDTO(); //조회 시 DB에 리스트가 없다면 nullException 예외

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchPostList(@RequestParam(value = "keyword") String keyword) {
        PostSearchRequestDTO postSearchRequestDTO = new PostSearchRequestDTO();
        postSearchRequestDTO.setKeyword(keyword);
        List<PostListDTO> rawPostList = postService.createSearchedPostListDTO(postSearchRequestDTO);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }


}
