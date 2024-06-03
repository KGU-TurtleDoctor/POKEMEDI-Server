package com.turtledoctor.kgu.post.repository;

import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostAndMember(Post post, Member member);
}
