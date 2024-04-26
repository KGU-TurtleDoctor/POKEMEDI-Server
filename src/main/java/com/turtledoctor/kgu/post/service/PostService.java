package com.turtledoctor.kgu.post.service;

import com.turtledoctor.kgu.converter.DateConverter;
import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.post.dto.request.*;
import com.turtledoctor.kgu.post.dto.response.PostDetailResponse;
import com.turtledoctor.kgu.post.dto.response.PostListResponse;
import com.turtledoctor.kgu.post.repository.PostRepository;
import com.turtledoctor.kgu.testPackage.repository.TempMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TempMemberRepository tempMemberRepository;

    @Transactional
    public Long createPost(CreatePostRequest createPostRequestDTO) {
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

    @Transactional
    public Long updatePost(UpdatePostRequest updatePostRequestDTO) {
        Post updatePost = postRepository.findById(updatePostRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("No post found with ID: " + updatePostRequestDTO.getPostId()));

        updatePost.updatePost(updatePostRequestDTO.getTitle(), updatePostRequestDTO.getBody());
        postRepository.save(updatePost);
        return updatePost.getId();
    }

    @Transactional
    public void deletePost(DeletePostRequest deletePostRequestDTO) {
        Post deletePost = postRepository.findById(deletePostRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("No post found with ID: " + deletePostRequestDTO.getPostId()));

        postRepository.delete(deletePost);
    }

    @Transactional(readOnly = true)
    public List<PostListResponse> createPostListDTO() {
        List<Post> rawPostList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostListResponse> postList = new ArrayList<>();

        for(Post post : rawPostList) {
            PostListResponse dto = PostListResponse.builder()
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

    @Transactional(readOnly = true)
    public List<PostListResponse> createSearchedPostListDTO(SearchPostRequest postSearchRequestDTO) {
        String keyword = postSearchRequestDTO.getKeyword();
        List<Post> rawSearchedPostList = postRepository.findAllByTitleContainingOrBodyContainingOrderByCreatedAtDesc(keyword, keyword);
        List<PostListResponse> postList = new ArrayList<>();

        for(Post post : rawSearchedPostList) {
            PostListResponse dto = PostListResponse.builder()
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

    @Transactional(readOnly = true)
    public PostDetailResponse createPostDetailDTO(GetPostDetailRequest getPostDetailRequestDTO) {
        Post post = postRepository.findById(getPostDetailRequestDTO.getPostId()).get();
        boolean isWriter;
        boolean isLiked = false; //PostLike 엔티티를 받아오고 작성 예정
        if(getPostDetailRequestDTO.getKakaoId() == post.getMember().getKakaoId()) isWriter = true;
        else isWriter = false;
        //if(post) - PostLike 엔티티를 받아오고 작성 예정

        PostDetailResponse dto = PostDetailResponse.builder()
                .title(post.getTitle())
                .content(post.getBody())
                .nickname(post.getMember().getNickname())
                .date(DateConverter.ConverteDate(post.getCreatedAt()))
                .likes(post.getLikes())
                .comments(post.getComments())
                .isWriter(isWriter)
                .isLiked(isLiked)
                .build();
        return dto;
    }
}
