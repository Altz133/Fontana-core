package com.fontana.backend.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fontana.backend.domain.service.userService;
import com.fontana.backend.domain.user.Users;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {

    private final userService theUserService;
    @PostMapping("/login")
    public void registerUser(@RequestBody Users userData){
        theUserService.addUser(userData);
    }
}
