package com.fontana.backend.user.service;

import com.fontana.backend.user.dto.UserMapper;
import com.fontana.backend.user.dto.UserRequestDTO;
import com.fontana.backend.user.repository.UsersRepository;
import com.fontana.backend.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class userServiceImpl implements userService{

    private final UsersRepository theUserRepository;

    @Override
    public void addUser(UserRequestDTO userData) {
        Users UserDTO = UserMapper.mapUser(userData);
        theUserRepository.save(UserDTO);
    }
}
