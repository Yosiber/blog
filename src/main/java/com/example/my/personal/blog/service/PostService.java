package com.example.my.personal.blog.service;

import com.example.my.personal.blog.persistence.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostEntity> getAllPosts();

    Optional<PostEntity> getPostById(Long Id);

    List<PostEntity> getPostByUserId(Long userId);

    void createPost(PostEntity post);
    void updatePost(Long Id, PostEntity post);
    void deletePostById(Long Id);

    List<PostEntity> searchPostByTitle(String title);
}
