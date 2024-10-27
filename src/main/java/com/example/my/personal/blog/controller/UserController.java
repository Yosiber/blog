package com.example.my.personal.blog.controller;

import com.example.my.personal.blog.persistence.entity.UserEntity;
import com.example.my.personal.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/record")
    public String recordPage(){
        return "/users/register";
    }

    @GetMapping(value = { "/login", "/"})
    public String loginPage(){
        return "/users/login";
    }

    @PostMapping("/register")
    public String registe(UserEntity user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/access")
    public String access(HttpSession session) {
        Optional<UserEntity> optionalUser = userService.getUserById(Long.parseLong(session.getAttribute("user_session_id").toString()));

        if (optionalUser.isPresent()) {
            session.setAttribute("user_session_id", optionalUser.get().getId());
            return "redirect:/posts/home";
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }


}
