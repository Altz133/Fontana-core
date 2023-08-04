package com.fontana.backend.user.service;

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
    private final UserDtoMapper userDtoMapper;

    @Override
    public void add(Users user) {
        userRepository.save(user);
    }

    //FIXME modify the way to set role once the database is configured

    @Override
    public void extractUserFromLDAP(List<Object> ldapDetails) {
        String[] nameParts = ldapDetails.get(10).toString().split("\\s+");

        UserDTO userDto = UserDTO.builder()
                .username(ldapDetails.get(7).toString())
                .firstName(nameParts[0])
                .lastName(nameParts[1])
                .role(null)
                .build();

        Users user = userDtoMapper.map(userDto);

        if (userRepository.findById(user.getUsername()).isEmpty()) {
            add(user);
        }
    }
}

