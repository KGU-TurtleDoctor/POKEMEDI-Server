package com.turtledoctor.kgu.comment.repository;

import com.turtledoctor.kgu.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findCommentsByPost_Id(Long PostId);
}
