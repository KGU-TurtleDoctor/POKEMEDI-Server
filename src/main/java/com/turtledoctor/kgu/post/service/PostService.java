package com.turtledoctor.kgu.post.service;

import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import com.turtledoctor.kgu.converter.DateConverter;
import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.entity.repository.MemberRepository;
import com.turtledoctor.kgu.post.dto.request.*;
import com.turtledoctor.kgu.post.dto.response.PostDetailResponse;
import com.turtledoctor.kgu.post.dto.response.PostListResponse;
import com.turtledoctor.kgu.post.exception.PostException;
import com.turtledoctor.kgu.post.repository.PostLikeRepository;
import com.turtledoctor.kgu.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.turtledoctor.kgu.exception.ErrorCode.POST_FORBIDDEN;
import static com.turtledoctor.kgu.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;

    @Value("${spring.jwt.secret}")
    private String secret;

    private JWTUtil jwtUtil;


    /** 게시글 기능 영역 */

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
        Optional<Post> optionalPost = postRepository.findById(updatePostRequestDTO.getPostId());
        if(optionalPost.isEmpty()) {
            throw new PostException(POST_NOT_FOUND);
        }

        jwtUtil = new JWTUtil(secret);
        String kakaoId = jwtUtil.getkakaoId(author);

        Post updatePost = optionalPost.get();
        if(!updatePost.getMember().getKakaoId().equals(kakaoId)) {
            throw new PostException(POST_FORBIDDEN);
        }

        updatePost.updatePost(updatePostRequestDTO.getTitle(), updatePostRequestDTO.getBody());
        postRepository.save(updatePost);
        return updatePost.getId();
    }

    @Transactional
    public boolean deletePost(DeletePostRequest deletePostRequestDTO, String author) {
        Optional<Post> optionalPost = postRepository.findById(deletePostRequestDTO.getPostId());
        if(optionalPost.isEmpty()) {
            throw new PostException(POST_NOT_FOUND);
        }

        jwtUtil = new JWTUtil(secret);
        String kakaoId = jwtUtil.getkakaoId(author);

        Post deletePost = optionalPost.get();
        if(!deletePost.getMember().getKakaoId().equals(kakaoId)) {
            throw new PostException(POST_FORBIDDEN);
        }

        postRepository.delete(deletePost);
        return true;
    }


    /** 게시글 기능 영역 */

    @Transactional(readOnly = true)
    public List<PostListResponse> getPostList(String author) {

        jwtUtil = new JWTUtil(secret);
        String kakaoId = jwtUtil.getkakaoId(author);

        List<Post> rawPostList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostListResponse> postList = new ArrayList<>();

        for(Post post : rawPostList) {
            boolean isWriter = kakaoId.equals(post.getMember().getKakaoId());
            PostListResponse dto = PostListResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getBody())
                    .nickname(post.getMember().getName())
                    .date(DateConverter.ConverteDate(post.getCreatedAt()))
                    .isWriter(isWriter)
                    .build();
            postList.add(dto);
        }
        return postList;
    }

    @Transactional(readOnly = true)
    public List<PostListResponse> getSearchedPostList(SearchPostRequest postSearchRequestDTO, String author) {

        jwtUtil = new JWTUtil(secret);
        String kakaoId = jwtUtil.getkakaoId(author);

        String keyword = postSearchRequestDTO.getKeyword();
        List<Post> rawSearchedPostList;

        if(keyword.isBlank()) {
            rawSearchedPostList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        else {
            rawSearchedPostList = postRepository.findAllByTitleContainingOrBodyContainingOrderByCreatedAtDesc(keyword, keyword);
        }

        List<PostListResponse> postList= new ArrayList<>();
        for(Post post : rawSearchedPostList) {
            boolean isWriter = kakaoId.equals(post.getMember().getKakaoId());
            PostListResponse dto = PostListResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getBody())
                    .nickname(post.getMember().getName())
                    .date(DateConverter.ConverteDate(post.getCreatedAt()))
                    .isWriter(isWriter)
                    .build();
            postList.add(dto);
        }
        return postList;
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(GetPostDetailRequest getPostDetailRequestDTO, String author) {

        Optional<Post> optionalPost = postRepository.findById(getPostDetailRequestDTO.getPostId());
        if(optionalPost.isEmpty()) {
            throw new PostException(POST_NOT_FOUND);
        }

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
    public List<PostListResponse> getMyPostList(String author) {

        jwtUtil = new JWTUtil(secret);
        String kakaoId = jwtUtil.getkakaoId(author);
        List<Post> rawMyPostList = postRepository.findAllByMemberOrderByCreatedAtDesc(memberRepository.findBykakaoId(kakaoId));
        List<PostListResponse> postList = new ArrayList<>();

        for(Post post : rawMyPostList) {
            PostListResponse dto = PostListResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getBody())
                    .nickname(post.getMember().getName())
                    .date(DateConverter.ConverteDate(post.getCreatedAt()))
                    .isWriter(true)
                    .build();
            postList.add(dto);
        }
        return postList;
    }

    @Transactional(readOnly = true)
    public List<PostListResponse> getMyPost(String author) {

        jwtUtil = new JWTUtil(secret);
        String kakaoId = jwtUtil.getkakaoId(author);

        Post rawMyPost = postRepository.findTop1ByMemberOrderByCreatedAtDesc(memberRepository.findBykakaoId(kakaoId));
        List<PostListResponse> post = new ArrayList<>();

        if(rawMyPost != null) {
            PostListResponse dto = PostListResponse.builder()
                    .id(rawMyPost.getId())
                    .title(rawMyPost.getTitle())
                    .content(rawMyPost.getBody())
                    .nickname(rawMyPost.getMember().getName())
                    .date(DateConverter.ConverteDate(rawMyPost.getCreatedAt()))
                    .isWriter(true)
                    .build();
            post.add(dto);
        }

        return post;
    }
}
