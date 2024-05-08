package com.turtledoctor.kgu.post.service;

import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import com.turtledoctor.kgu.converter.DateConverter;
import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.entity.PostLike;
import com.turtledoctor.kgu.entity.repository.MemberRepository;
import com.turtledoctor.kgu.post.dto.request.*;
import com.turtledoctor.kgu.post.dto.response.PostDetailResponse;
import com.turtledoctor.kgu.post.dto.response.PostListResponse;
import com.turtledoctor.kgu.post.repository.PostLikeRepository;
import com.turtledoctor.kgu.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;

    @Value("${spring.jwt.secret}")
    private String secret;

    private JWTUtil jwtUtil;

    @Transactional
    public Long createPost(CreatePostRequest createPostRequestDTO, String author) {

        jwtUtil = new JWTUtil(secret);
        String kakaoId = jwtUtil.getkakaoId(author);
        Post newPost = postRepository.save(Post.builder()
                .member(memberRepository.findBykakaoId(kakaoId))
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
    public Long updatePost(UpdatePostRequest updatePostRequestDTO, String author) {
        Post updatePost = postRepository.findById(updatePostRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("No post found with ID: " + updatePostRequestDTO.getPostId()));

        updatePost.updatePost(updatePostRequestDTO.getTitle(), updatePostRequestDTO.getBody());
        postRepository.save(updatePost);
        return updatePost.getId();
    }

    @Transactional
    public boolean deletePost(DeletePostRequest deletePostRequestDTO, String author) {
        Post deletePost = postRepository.findById(deletePostRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("No post found with ID: " + deletePostRequestDTO.getPostId()));

        postRepository.delete(deletePost);
        return true;
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
                    .nickname(post.getMember().getName())
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
                    .nickname(post.getMember().getName())
                    .date(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                    .build();
            postList.add(dto);
        }
        return postList;
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetailDTO(GetPostDetailRequest getPostDetailRequestDTO, String author) {

        jwtUtil = new JWTUtil(secret);
        String kakaoId = jwtUtil.getkakaoId(author);
        Post post = postRepository.findById(getPostDetailRequestDTO.getPostId()).get();
        Member member = memberRepository.findBykakaoId(kakaoId);
        boolean isWriter = post.getMember().getKakaoId().equals(kakaoId);
        boolean isLiked = postLikeRepository.existsByPostAndMember(post, member);

        PostDetailResponse dto = PostDetailResponse.builder()
                .title(post.getTitle())
                .content(post.getBody())
                .nickname(post.getMember().getName())
                .date(DateConverter.ConverteDate(post.getCreatedAt()))
                .likes(post.getLikes())
                .comments(post.getComments())
                .isWriter(isWriter)
                .isLiked(isLiked)
                .build();
        return dto;
    }

    @Transactional(readOnly = true)
    public List<PostListResponse> getMyPostListDTO(String author) {

        jwtUtil = new JWTUtil(secret);
        String kakaoId = jwtUtil.getkakaoId(author);
        List<Post> rawMyPostList = postRepository.findAllByMember(memberRepository.findBykakaoId(kakaoId));
        List<PostListResponse> postList = new ArrayList<>();

        for(Post post : rawMyPostList) {
            PostListResponse dto = PostListResponse.builder()
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
