package com.fontana.backend.domain.user.service;

import com.fontana.backend.domain.user.dtos.UserMapper;
import com.fontana.backend.domain.user.dtos.UserRequestDTO;
import com.fontana.backend.domain.user.repository.UsersRepository;
import com.fontana.backend.domain.user.entity.Users;
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
