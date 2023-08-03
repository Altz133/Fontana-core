package com.fontana.backend.domain.user.controller;

import com.fontana.backend.domain.user.dtos.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fontana.backend.domain.user.service.userService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fontana/api/v1/")
@RequiredArgsConstructor
public class userController {

    private final userService theUserService;
    @PostMapping("/users")
    public void registerUser(@RequestBody UserRequestDTO userData){
        theUserService.addUser(userData);
    }
}
