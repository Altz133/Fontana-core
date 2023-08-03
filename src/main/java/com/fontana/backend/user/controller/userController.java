package com.fontana.backend.user.controller;

import com.fontana.backend.user.dto.UserRequestDTO;
import com.fontana.backend.user.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
