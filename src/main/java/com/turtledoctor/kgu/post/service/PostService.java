package com.turtledoctor.kgu.post.service;

import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.post.dto.request.CreatePostRequest;
import com.turtledoctor.kgu.post.dto.request.DeletePostRequest;
import com.turtledoctor.kgu.post.dto.request.SearchPostRequest;
import com.turtledoctor.kgu.post.dto.request.UpdatePostRequest;
import com.turtledoctor.kgu.post.dto.response.PostResponse;
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

    public Long createPost(CreatePostRequest createPostRequestDTO) {
        Post newPost = postRepository.save(Post.builder()
                .member(tempMemberRepository.findByKakaoId(createPostRequestDTO.getKakaoId().toString()))
                .title(createPostRequestDTO.getTitle())
                .body(createPostRequestDTO.getBody())
                .likes(0L)
                .comments(0L)
                .commentList(new ArrayList<>())
                .build()
        );
        return newPost.getId();
    }

    public Long updatePost(UpdatePostRequest updatePostRequestDTO) {
        Post updatePost = postRepository.findById(updatePostRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("No post found with ID: " + updatePostRequestDTO.getPostId()));

        updatePost.updatePost(updatePostRequestDTO.getTitle(), updatePostRequestDTO.getBody());
        postRepository.save(updatePost);
        return updatePost.getId();
    }

    public void deletePost(DeletePostRequest deletePostRequestDTO) {
        Post deletePost = postRepository.findById(deletePostRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("No post found with ID: " + deletePostRequestDTO.getPostId()));

        postRepository.delete(deletePost);
    }

    public List<PostResponse> createPostListDTO() {
        List<Post> rawPostList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostResponse> postList = new ArrayList<>();

        for(Post post : rawPostList) {
            PostResponse dto = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getBody())
                    .nickname(post.getMember().getName())
                    .date(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                    .build();
            postList.add(dto);
        }
        return postList;
    }

    public List<PostResponse> createSearchedPostListDTO(SearchPostRequest postSearchRequestDTO) {
        String keyword = postSearchRequestDTO.getKeyword();
        List<Post> rawSearchedPostList = postRepository.findAllByTitleContainingOrBodyContainingOrderByCreatedAtDesc(keyword, keyword);
        List<PostResponse> postList = new ArrayList<>();

        for(Post post : rawSearchedPostList) {
            PostResponse dto = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getBody())
                    .nickname(post.getMember().getName())
                    .date(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                    .build();
            postList.add(dto);
        }
        return postList;
    }
}
