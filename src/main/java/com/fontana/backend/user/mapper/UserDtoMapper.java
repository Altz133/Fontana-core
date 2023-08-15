package com.fontana.backend.user.mapper;

import com.fontana.backend.user.dto.UserDTO;
import com.fontana.backend.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapper {

    public UserDTO map(User user) {

        return UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public User map(UserDTO userDto) {

        return User.builder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .role(userDto.getRole())
                .build();
    }
}
