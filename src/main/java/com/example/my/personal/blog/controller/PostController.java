package com.example.my.personal.blog.controller;


import com.example.my.personal.blog.persistence.entity.CommentEntity;
import com.example.my.personal.blog.persistence.entity.PostEntity;
import com.example.my.personal.blog.persistence.entity.UserEntity;
import com.example.my.personal.blog.service.PostService;
import com.example.my.personal.blog.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "/posts/home";
    }

    @GetMapping("/new")
    public String newPostPage() {
        return "/posts/create-post";
    }

    @PostMapping("/create")
    public String createPost(PostEntity post, HttpSession session) {
        post.setCreatedAt(LocalDateTime.now());

        UserEntity user = userService.getUserById(Long.parseLong(session.getAttribute("user_session_id").toString())).get();
        post.setUser(user);

        postService.createPost(post);
        return "redirect:/posts/home";
    }

    @GetMapping("/postPage/{id}")
    public String postPage(@PathVariable Long id, Model model) {
        PostEntity posts = postService.getPostById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Post Id"));
        List<CommentEntity> comments = posts.getComments();

        model.addAttribute("posts", posts);
        model.addAttribute("comments", comments);
        return "/posts/post-page";
    }

    @GetMapping("/mine")
    public String myPosts(Model model, HttpSession session) {
        Long userId = Long.parseLong(session.getAttribute("user_session_id").toString());
        List<PostEntity> posts = postService.getPostByUserId(userId);

        model.addAttribute("posts", posts);
        return "/posts/my-posts";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        PostEntity posts = postService.getPostById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Post Id"));
        model.addAttribute("posts", posts);
        return "/posts/update-posts";
    }

    @PostMapping("/update")
    public String updatePost(@RequestParam("idPosts") Long id, PostEntity posts) {
        postService.updatePost(id, posts);
        return "redirect:/posts/mine";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return "redirect:/posts/mine";
    }

    @GetMapping("/search")
    public String searchPost(@RequestParam("title") String title, Model model) {
        List<PostEntity> posts = postService.searchPostByTitle(title);
        model.addAttribute("posts", posts);
        return "/posts/home";
    }
}
