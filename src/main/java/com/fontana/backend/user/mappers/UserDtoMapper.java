package com.fontana.backend.user.mappers;

import com.fontana.backend.user.dtos.UserDTO;
import com.fontana.backend.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapper {

    public UserDTO map(User user) {
        UserDTO userDto = UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();

        return userDto;
    }

    public User map(UserDTO userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .role(userDto.getRole())
                .build();

        return user;
    }
}
