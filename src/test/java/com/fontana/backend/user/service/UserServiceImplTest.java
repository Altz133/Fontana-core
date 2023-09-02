package com.fontana.backend.user.service;

import com.fontana.backend.role.entity.Role;
import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.role.repository.RoleRepository;
import com.fontana.backend.user.dto.UserDTO;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.mapper.UserDtoMapper;
import com.fontana.backend.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User testUser;
    private UserDTO testUserDto;
    private final List<UserDTO> mockUserDtos = new ArrayList<>();
    private Role role;

    @BeforeEach
    public void init() {
        testUser = initUser();

        testUserDto = initUserDto();

        mockUserDtos.add(testUserDto);
        role = new Role();
    }

    @Test
    public void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userRepository.save(testUser);
        verify(userRepository).save(testUser);
    }

    @Test
    public void testFindAllUsers() {
        role.setName(RoleType.OPERATOR.name());

        when(userRepository.findAll()).thenReturn(new ArrayList<>(Collections.singletonList(testUser)));
        when(userDtoMapper.map(any(User.class))).thenReturn(mockUserDtos.get(0));

        List<UserDTO> result = userServiceImpl.findAll(null);

        assertEquals(mockUserDtos.size(), result.size());
    }

    private User initUser() {
        return User.builder()
                .username("testUser")
                .firstName("Test")
                .lastName("User")
                .role(roleRepository.findRoleByName(RoleType.OPERATOR.name()))
                .logs(null)
                .build();
    }

    private UserDTO initUserDto() {
        return UserDTO.builder()
                .username("testUser")
                .firstName("Test")
                .lastName("User")
                .role(roleRepository.findRoleByName(RoleType.OPERATOR.name()))
                .build();
    }
}