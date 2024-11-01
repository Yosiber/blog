package com.example.my.personal.blog.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String title;

    @Column(columnDefinition = "TEXT")
    public String content;
    public LocalDateTime createdAt;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private UserEntity user;

    @OneToMany (mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();
}
