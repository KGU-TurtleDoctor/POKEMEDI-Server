package com.turtledoctor.kgu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String fileName;

    @Column(nullable = false, length = 200)
    private String filePath;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //이미지가 필요한 엔티티와 ManyToOne으로 매핑시킬 것.
}
