package com.fontana.backend.user.service;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.role.entity.Role;
import com.fontana.backend.role.repository.RoleRepository;
import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.user.dto.UserDTO;
import com.fontana.backend.user.dto.UserUpdateRequestDTO;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.mapper.UserDtoMapper;
import com.fontana.backend.user.repository.UserRepository;
import com.fontana.backend.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${user.not-found-msg}")
    private String notFoundMsg;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDtoMapper userDtoMapper;
    private final AuthUtils authUtils;

    @Override
    public List<UserDTO> findAll(RoleType roleType) {
        List<User> users;

        if (roleType != null) {
            Role role = roleRepository.findRoleByName(roleType.name());
            users = userRepository.findAllByRole(role);
        } else {
            users = userRepository.findAll();
        }

        return users.stream()
                .map(userDtoMapper::map)
                .toList();
    }

    @Override
    public UserDTO findByUsername() {
        String username = authUtils.getAuthentication().getPrincipal().toString();
        log.info(username);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(notFoundMsg));

        return userDtoMapper.map(user);
    }

    @Override
    public void add(User user) {
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
                .role(roleRepository.findAllByName(RoleType.VIEWER.name()).get(0))
                .build();

        User user = userDtoMapper.map(userDto);

        if (userRepository.findById(user.getUsername()).isEmpty()) {
            add(user);
        }
    }

    @Override
    public ResponseEntity<?> updateRole(String username, UserUpdateRequestDTO userUpdateRequestDTO) {
        String authority = authUtils.extractAuthenticatedAuthority();

        if (authority.equals(RoleType.VIEWER.name()) || authority.equals(RoleType.OPERATOR.name())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Role changedRole = roleRepository.findRoleByName(userUpdateRequestDTO.getRoleType().name());
        log.info("Current role: " + authUtils.extractAuthenticatedAuthority() + ", new role: " + changedRole);

        User existing = userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(notFoundMsg));

        userRepository.save(buildUpdatedUser(existing, changedRole));

        return ResponseEntity.ok().build();
    }

    private User buildUpdatedUser(User existing, Role changedRole) {

        return User.builder()
                .username(existing.getUsername())
                .firstName(existing.getFirstName())
                .lastName(existing.getLastName())
                .logs(existing.getLogs())
                .role(changedRole)
                .lastRoleChange(LocalDateTime.now())
                .build();
    }
}