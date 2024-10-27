package com.example.my.personal.blog.service.impl;

import com.example.my.personal.blog.persistence.entity.CommentEntity;
import com.example.my.personal.blog.persistence.repository.CommentRepository;
import com.example.my.personal.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Optional<CommentEntity> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public void createComment(CommentEntity comment) {
        commentRepository.save(comment);

    }

    @Override
    public void updateComment(Long id, CommentEntity comment) {
        CommentEntity commentDB = getCommentById(id).orElseThrow(()-> new InvalidParameterException("Invalid comment id"));
        comment.setContent(commentDB.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);

    }
}
