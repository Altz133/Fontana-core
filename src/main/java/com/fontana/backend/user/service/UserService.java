package com.fontana.backend.user.service;

import com.fontana.backend.user.entity.Users;

import java.util.List;

public interface UserService {

    void add(Users user);

    void extractUserFromLDAP(List<Object> ldapDetails);
}
