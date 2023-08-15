package com.fontana.backend.user.service;

import com.fontana.backend.user.dto.UserDTO;
import com.fontana.backend.user.entity.User;

import java.util.List;

public interface UserService {

    UserDTO findByUsername();

    void add(User user);

    void extractUserFromLDAP(List<Object> ldapDetails);
}
