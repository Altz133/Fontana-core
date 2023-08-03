package com.fontana.backend.user.service;

import com.fontana.backend.user.dtos.UserDTO;

public interface UserService {

    void addUser(UserDTO userDTO);

    UserDTO extractUserFromLDAP();
}
