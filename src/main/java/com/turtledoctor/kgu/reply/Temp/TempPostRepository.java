package com.turtledoctor.kgu.reply.Temp;

import com.turtledoctor.kgu.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempPostRepository extends JpaRepository<Post,Long> {
}
