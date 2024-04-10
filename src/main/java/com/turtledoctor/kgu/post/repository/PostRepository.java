package com.turtledoctor.kgu.post.repository;

import com.turtledoctor.kgu.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //게시글 게시
    //게시글 수정
    //게시글 삭제
    //게시글 좋아요
    //게시글 검색 - 작성자?
    //게시글 검색 - 제목?

    //게시글 수정
    //void update(Long postId);

    //게시글 삭제
    //void delete(Long postId);

    //게시글 좋아요
    //void like(Long postId, Long memberId);

    //게시글 검색 - 작성자
    List<Post> findAllByMemberId(Long memberId);

    List<Post> findAllByTitleContaining(String keyword);
}
