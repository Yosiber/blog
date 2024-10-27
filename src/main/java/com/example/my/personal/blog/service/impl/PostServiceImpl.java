package com.example.my.personal.blog.service.impl;


import com.example.my.personal.blog.persistence.entity.PostEntity;
import com.example.my.personal.blog.persistence.repository.PostRepository;
import com.example.my.personal.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<PostEntity> getPostById(Long Id) {
        return postRepository.findById(Id);
    }

    @Override
    public List<PostEntity> getPostByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public void createPost(PostEntity post) {
        postRepository.save(post);
    }

    @Override
    public void updatePost(Long id, PostEntity post) {
        PostEntity postDB = getPostById(id).orElseThrow(() -> new InvalidParameterException("Invalid Post Id"));
        postDB.setTitle(post.getTitle());
        postDB.setContent(post.getContent());
        postRepository.save(postDB);

    }

    @Override
    public void deletePostById(Long Id) {
        postRepository.deleteById(Id);
    }

    @Override
    public List<PostEntity> searchPostByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title);
    }
}
