package com.fontana.backend.domain.service;

import com.fontana.backend.domain.user.UsersRepository;
import com.fontana.backend.domain.user.Users;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class userServiceImpl implements userService{

    private final UsersRepository theUserRepository;

    @Override
    public void addUser(Users userData) {
        theUserRepository.save(userData);
    }
}
