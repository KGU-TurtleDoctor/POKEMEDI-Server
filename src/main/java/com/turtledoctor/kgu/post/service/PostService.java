package com.turtledoctor.kgu.post.service;

import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.post.DTO.CreatePostRequestDTO;
import com.turtledoctor.kgu.post.repository.PostRepository;
import com.turtledoctor.kgu.testPackage.repository.TempMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TempMemberRepository tempMemberRepository;

    public Long createPost(CreatePostRequestDTO createPostRequestDTO) {
        Post newPost = postRepository.save(Post.builder()
                .member(tempMemberRepository.findByKakaoId(createPostRequestDTO.getKakaoId()))
                .title(createPostRequestDTO.getTitle())
                .body(createPostRequestDTO.getBody())
                .likes(0L)
                .comments(0L)
                .commentList(new ArrayList<>())
                .build()
        );
        return newPost.getId();
    }

    //public Long updatePost(Long kakaoId) {
    //}
}
