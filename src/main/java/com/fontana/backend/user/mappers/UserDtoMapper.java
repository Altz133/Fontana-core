package com.fontana.backend.user.mappers;

import com.fontana.backend.user.dtos.UserDTO;
import com.fontana.backend.user.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapper {

    public UserDTO map(Users user) {
        UserDTO userDto = UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();

        return userDto;
    }

    public Users map(UserDTO userDto) {
        Users user = Users.builder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .role(userDto.getRole())
                .build();

        return user;
    }
}
