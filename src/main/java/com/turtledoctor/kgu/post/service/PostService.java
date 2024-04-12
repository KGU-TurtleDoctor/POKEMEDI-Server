package com.turtledoctor.kgu.post.service;

import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.post.DTO.CreatePostRequestDTO;
import com.turtledoctor.kgu.post.DTO.PostListDTO;
import com.turtledoctor.kgu.post.repository.PostRepository;
import com.turtledoctor.kgu.testPackage.repository.TempMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public List<PostListDTO> createPostListDTO() {
        List<Post> rawPostList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostListDTO> postList = new ArrayList<>();

        for(Post post : rawPostList) {
            PostListDTO dto = PostListDTO.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getBody())
                    .nickname(post.getMember().getNickname())
                    .date(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                    .build();
            postList.add(dto);
        }
        return postList;
    }
}
