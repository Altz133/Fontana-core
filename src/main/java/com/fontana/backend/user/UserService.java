package com.fontana.backend.user;

import com.fontana.backend.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO findByUsername();

    void add(User user);

    void extractUserFromLDAP(List<Object> ldapDetails);
}
