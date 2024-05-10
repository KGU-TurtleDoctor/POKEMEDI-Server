package com.turtledoctor.kgu.post.controller;

import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import com.turtledoctor.kgu.post.dto.request.*;
import com.turtledoctor.kgu.post.dto.response.PostListResponse;
import com.turtledoctor.kgu.post.service.PostService;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ResponseDTO> createPost(@CookieValue(name = "Authorization") String author, @RequestBody CreatePostRequest createPostRequestDTO) {

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.createPost(createPostRequestDTO, author))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<ResponseDTO> updatePost(@CookieValue(name = "Authorization") String author, @PathVariable("postId") Long postId, @RequestBody UpdatePostRequest updatePostRequestDTO) {
        updatePostRequestDTO.setPostId(postId);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.updatePost(updatePostRequestDTO, author))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ResponseDTO> deletePost(@CookieValue(name = "Authorization") String author, @PathVariable("postId") Long postId) {
        DeletePostRequest deletePostRequestDTO = new DeletePostRequest();
        deletePostRequestDTO.setPostId(postId);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.deletePost(deletePostRequestDTO, author))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getPostList() {
        List<PostListResponse> rawPostList = postService.createPostListDTO(); //조회 시 DB에 리스트가 없다면 nullException 예외

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<ResponseDTO> searchPostList(@PathVariable(name = "keyword", required = false) String keyword) {

        SearchPostRequest postSearchRequestDTO = new SearchPostRequest();
        postSearchRequestDTO.setKeyword(keyword);
        List<PostListResponse> rawPostList = postService.createSearchedPostListDTO(postSearchRequestDTO);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/detail/{postId}")
    public ResponseEntity<ResponseDTO> getPostDetail(@CookieValue(name = "Authorization") String author, @PathVariable("postId") Long postId) {
        GetPostDetailRequest getPostDetailRequestDTO = new GetPostDetailRequest();
        getPostDetailRequestDTO.setPostId(postId);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.getPostDetailDTO(getPostDetailRequestDTO, author))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/myPostList")  //내가 쓴 게시글 조회
    public ResponseEntity<ResponseDTO> getMyPostList(@CookieValue(name = "Authorization") String author) {
        List<PostListResponse> rawMyPostList = postService.getMyPostListDTO(author);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawMyPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }
}
