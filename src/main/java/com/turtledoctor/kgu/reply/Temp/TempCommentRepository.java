package com.turtledoctor.kgu.reply.Temp;

import com.turtledoctor.kgu.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempCommentRepository extends JpaRepository<Comment, Long> {
}
