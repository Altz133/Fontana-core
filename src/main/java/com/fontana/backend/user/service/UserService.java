package com.fontana.backend.user.service;

import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.user.dto.UserDTO;
import com.fontana.backend.user.dto.UserUpdateRequestDTO;
import com.fontana.backend.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll(RoleType roleType);

    UserDTO findByUsername();

    void add(User user);

    void extractUserFromLDAP(List<Object> ldapDetails);

    ResponseEntity<?> updateRole(String username, UserUpdateRequestDTO userUpdateRequestDTO);
}
