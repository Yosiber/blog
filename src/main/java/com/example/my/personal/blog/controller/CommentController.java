package com.example.my.personal.blog.controller;

import com.example.my.personal.blog.persistence.entity.CommentEntity;
import com.example.my.personal.blog.persistence.entity.PostEntity;
import com.example.my.personal.blog.persistence.entity.UserEntity;
import com.example.my.personal.blog.service.CommentService;
import com.example.my.personal.blog.service.PostService;
import com.example.my.personal.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/addComment")
    public String addComment(@RequestParam ("postId") Long postId, CommentEntity comment, HttpSession session) {

        UserEntity user = userService.getUserById(Long.parseLong(session.getAttribute("user_session_id").toString())).get();
        PostEntity posts = postService.getPostById(postId).orElseThrow(()-> new IllegalArgumentException("Invalid post id"));

        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setPost(posts);

        commentService.createComment(comment);
        return "redirect:/posts/postPage/" + postId;
    }

    @GetMapping("/edit/{id}")
    public String editComment(@PathVariable Long id, Model model) {
        CommentEntity comment = commentService.getCommentById(id).orElseThrow(() -> new IllegalArgumentException("¡Invalid comment id!"));
        model.addAttribute("comment", comment);
        return "/posts/update-comment";
    }

    @PostMapping("/update")
    public String updateComment(@RequestParam("IdComment") Long id, CommentEntity comment) {
        CommentEntity commentDB = commentService.getCommentById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comment id"));
        commentDB.setContent(comment.getContent());
        commentService.updateComment(id, commentDB);
        return "redirect:/posts/postPage/" + commentDB.getPost().getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        CommentEntity comment = commentService.getCommentById(id).orElseThrow(() -> new IllegalArgumentException("¡Invalid comment id!"));
        commentService.deleteComment(id);
        return "redirect:/posts/postPage/" + comment.getPost().getId();
    }


    @GetMapping("/cancel/{id}")
    public String cancelEditComment(@PathVariable Long id) {
        CommentEntity comment = commentService.getCommentById(id).orElseThrow(() -> new IllegalArgumentException("¡Invalid comment id!"));
        return "redirect:/posts/postPage/" + comment.getPost().getId();
    }

}

