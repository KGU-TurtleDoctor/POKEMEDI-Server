package com.turtledoctor.kgu.entity;

import com.turtledoctor.kgu.entity.base.BaseEntity;
import com.turtledoctor.kgu.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "User")
public class Member extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname",nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(nullable = false, length = 100)
    private String kakaoId;

    @Column(name = "role", columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Post> postList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Reply> replyList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ChatHistory> chatHistoryList;

    public void updateMember(String nickname){
        this.nickname = nickname;
    }
}
