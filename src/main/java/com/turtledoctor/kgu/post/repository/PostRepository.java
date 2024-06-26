package com.turtledoctor.kgu.post.repository;

import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    //게시글 조회
    //게시글 게시
    //게시글 수정
    //게시글 삭제
    //게시글 좋아요
    //게시글 검색 - 작성자
    //게시글 검색 - 제목

    //게시글 좋아요
    //void like(Long postId, Long memberId);

    //게시글 검색 - 제목+본문
    List<Post> findAllByTitleContainingOrBodyContainingOrderByCreatedAtDesc(String titleKeyword, String bodyKeyword);

    //게시글 검색 - 작성자
    List<Post> findAllByMemberOrderByCreatedAtDesc(Member member);

    //나의 게시글 한 개 조회
    Post findTop1ByMemberOrderByCreatedAtDesc(Member member);
}
