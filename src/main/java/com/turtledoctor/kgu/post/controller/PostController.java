package com.turtledoctor.kgu.post.controller;

import com.turtledoctor.kgu.post.dto.request.CreatePostRequest;
import com.turtledoctor.kgu.post.dto.request.DeletePostRequest;
import com.turtledoctor.kgu.post.dto.request.SearchPostRequest;
import com.turtledoctor.kgu.post.dto.request.UpdatePostRequest;
import com.turtledoctor.kgu.post.dto.response.PostResponse;
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
    public ResponseEntity<ResponseDTO> createPost(@RequestBody CreatePostRequest createPostRequestDTO) {

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.createPost(createPostRequestDTO))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updatePost(@RequestBody UpdatePostRequest updatePostRequestDTO) {

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.updatePost(updatePostRequestDTO))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deletePost(@RequestBody DeletePostRequest deletePostRequestDTO) {

        postService.deletePost(deletePostRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getPostList() {
        List<PostResponse> rawPostList = postService.createPostListDTO(); //조회 시 DB에 리스트가 없다면 nullException 예외

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<ResponseDTO> searchPostList(@PathVariable("keyword") String keyword) {
        SearchPostRequest postSearchRequestDTO = new SearchPostRequest();
        postSearchRequestDTO.setKeyword(keyword);
        List<PostResponse> rawPostList = postService.createSearchedPostListDTO(postSearchRequestDTO);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }


}
