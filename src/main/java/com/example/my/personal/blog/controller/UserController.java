package com.example.my.personal.blog.controller;

import com.example.my.personal.blog.persistence.entity.UserEntity;
import com.example.my.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;


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
        userService.createUser(user);
        return "redirect:/login";
    }


}
