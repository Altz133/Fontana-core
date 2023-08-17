package com.fontana.backend.user.controller;

import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.user.dto.UserUpdateRequestDTO;
import com.fontana.backend.user.service.UserService;
import com.fontana.backend.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> findAll(@RequestParam(required = false) RoleType roleType) {
        return userService.findAll(roleType);
    }

    @GetMapping(USER_FIND_ACTIVE)
    public UserDTO findByUsername() {
        return userService.findByUsername();
    }

    @PutMapping(USER_UPDATE_ROLE)
    public ResponseEntity<?> updateRole(@PathVariable("username") String username,
                                        @RequestBody @Validated UserUpdateRequestDTO userUpdateRequestDTO) {
        return userService.updateRole(username, userUpdateRequestDTO);
    }
}
