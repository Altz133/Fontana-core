package com.fontana.backend.user.service;

import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.role.repository.RoleRepository;
import com.fontana.backend.user.dtos.UserDTO;
import com.fontana.backend.user.entity.Users;
import com.fontana.backend.user.mappers.UserDtoMapper;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDtoMapper userDtoMapper;

    @Override
    public void add(Users user) {
        userRepository.save(user);
    }

    /**
     * This method is responsible for processing LDAP details and extracting user information to create a UserDTO.
     * Every logged user is assigned to VIEWER role by default.
     *
     * @param ldapDetails list of LDAP details. Index 10 contains the full name, and index 7 contains the username.
     */
    @Override
    public void extractUserFromLDAP(List<Object> ldapDetails) {
        String[] nameParts = ldapDetails.get(10).toString().split("\\s+");

        UserDTO userDto = UserDTO.builder()
                .username(ldapDetails.get(7).toString())
                .firstName(nameParts[0])
                .lastName(nameParts[1])
                .role(roleRepository.findAllByRoleType(RoleType.VIEWER).get(0))
                .build();

        Users user = userDtoMapper.map(userDto);

        if (userRepository.findById(user.getUsername()).isEmpty()) {
            add(user);
        }
    }
}