package com.fontana.backend.domain.service;

import com.fontana.backend.domain.user.Users;
import org.springframework.stereotype.Service;

@Service
public interface userService {

    public void addUser(Users userData);
}
