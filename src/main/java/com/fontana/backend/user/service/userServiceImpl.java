package com.fontana.backend.user.service;

import com.fontana.backend.user.dtos.UserDTO;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.mappers.UserDtoMapper;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Override
    public void addUser(UserDTO userDto) {
        User user = userDtoMapper.map(userDto);
        userRepository.save(user);
    }

    @Override
    public UserDTO extractUserFromLDAP() {
        return null;
    }
}

