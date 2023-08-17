package com.fontana.backend.user.service;

import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.role.repository.RoleRepository;
import com.fontana.backend.user.dto.UserDTO;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.mapper.UserDtoMapper;
import com.fontana.backend.user.repository.UserRepository;
import com.fontana.backend.utils.AuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private User testUser;
    @BeforeEach
    public void init() {
        testUser = User.builder()
                .username("testUser")
                .firstName("Test")
                .lastName("User")
                .role(roleRepository.findRoleByName(RoleType.OPERATOR.name()))
                .logs(null)
                .build();
    }

    @Test
    public void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userRepository.save(testUser);
        verify(userRepository).save(testUser);
    }
}