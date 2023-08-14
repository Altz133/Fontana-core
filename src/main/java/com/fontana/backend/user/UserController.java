package com.fontana.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(USER_FIND_BY_USERNAME)
    public UserDTO findByUsername() {
        return userService.findByUsername();
    }
}
