package com.example.my.personal.blog.service;

import com.example.my.personal.blog.persistence.entity.CommentEntity;

import java.util.Optional;

public interface CommentService {
    Optional<CommentEntity> getCommentById(Long id);
    void createComment(CommentEntity comment);
    void updateComment(Long id, CommentEntity comment);
    void deleteComment(Long id);
}
