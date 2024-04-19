package com.turtledoctor.kgu.entity;

import com.turtledoctor.kgu.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //기존 ERD에 title 없어서 추가. 타입은 어떻게?
    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(nullable = false)
    private Long likes;

    @Column(nullable = false)
    private Long comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    public void updatePost(String title, String body){
        this.title = title;
        this.body = body;
    }

    public void plusLikes(){
        this.likes++;
    }

    public void minusLikes(){
        this.likes--;
    }

    public void plusComments(){
        this.comments++;
    }

    public void minusComments(){
        this.comments--;
    }
}
