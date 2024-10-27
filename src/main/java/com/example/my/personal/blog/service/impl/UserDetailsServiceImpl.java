package com.example.my.personal.blog.service.impl;

import com.example.my.personal.blog.persistence.entity.UserEntity;
import com.example.my.personal.blog.persistence.repository.UserRepository;
import com.example.my.personal.blog.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<UserEntity> Optionaluser = userService.getUserByUsername(username);

       if (Optionaluser.isPresent()) {
           session.setAttribute("user_session_id", Optionaluser.get().getId());
           UserEntity user = Optionaluser.get();
           return User.builder()
                   .username(user.getUsername())
                   .password(user.getPassword())
                   .roles()
                   .build();
       } else {
           throw new UsernameNotFoundException("User not found");
       }
    }
}
