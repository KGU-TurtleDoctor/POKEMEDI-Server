package com.turtledoctor.kgu.post.controller;

import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.post.dto.request.*;
import com.turtledoctor.kgu.post.dto.response.PostListResponse;
import com.turtledoctor.kgu.post.service.PostService;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;


    /** 게시글 기능 영역 */

    @PostMapping("/create")  //게시글 셍성
    public ResponseEntity<ResponseDTO> createPost(@CookieValue(name = "Authorization") String author, @RequestBody CreatePostRequest createPostRequestDTO) {

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.createPost(createPostRequestDTO, author))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/update/{postId}")  //게시글 수정
    public ResponseEntity<ResponseDTO> updatePost(@CookieValue(name = "Authorization") String author, @PathVariable("postId") Long postId, @RequestBody UpdatePostRequest updatePostRequestDTO) {
        updatePostRequestDTO.setPostId(postId);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.updatePost(updatePostRequestDTO, author))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/delete/{postId}")  //게시글 삭제
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


    /** 커뮤니티 페이지 영역 */

    @GetMapping("/list")  //게시글 전체 조회
    public ResponseEntity<ResponseDTO> getPostList(@CookieValue(name = "Authorization") String author) {
        List<PostListResponse> rawPostList = postService.getPostList(author); //조회 시 DB에 리스트가 없다면 nullException 예외

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping({"/search/{keyword}", "/search/"})  //게시글 검색 조회
    public ResponseEntity<ResponseDTO> searchPostList(@CookieValue(name = "Authorization") String author, @PathVariable(name = "keyword", required = false) String keyword) {
        if (keyword == null) {
            keyword ="";
        }

        SearchPostRequest postSearchRequestDTO = new SearchPostRequest();
        postSearchRequestDTO.setKeyword(keyword);
        List<PostListResponse> rawPostList = postService.getSearchedPostList(postSearchRequestDTO, author);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }


    /** 게시글 상세 페이지 영역 */

    @GetMapping("/detail/{postId}")  //게시글 상세 조회
    public ResponseEntity<ResponseDTO> getPostDetail(@CookieValue(name = "Authorization") String author, @PathVariable("postId") Long postId) {
        GetPostDetailRequest getPostDetailRequestDTO = new GetPostDetailRequest();
        getPostDetailRequestDTO.setPostId(postId);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(postService.getPostDetail(getPostDetailRequestDTO, author))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }


    /** 마이 페이지 영역 */

    @GetMapping("/myPostList")  //내가 쓴 게시글 조회
    public ResponseEntity<ResponseDTO> getMyPostList(@CookieValue(name = "Authorization") String author) {
        List<PostListResponse> rawMyPostList = postService.getMyPostList(author);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawMyPostList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/myPost")  //내가 쓴 게시글 하나 조회
    public ResponseEntity<ResponseDTO> getMyPost(@CookieValue(name = "Authorization") String author) {
        List<PostListResponse> rawMyPost = postService.getMyPost(author);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(rawMyPost)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }
}
