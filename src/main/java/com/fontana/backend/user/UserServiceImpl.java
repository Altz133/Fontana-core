package com.fontana.backend.user;

import com.fontana.backend.exception.customExceptions.NotFoundException;
import com.fontana.backend.role.RoleRepository;
import com.fontana.backend.role.RoleType;
import com.fontana.backend.user.*;
import com.fontana.backend.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${user.not-found-msg}")
    private String notFoundMsg;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDtoMapper userDtoMapper;
    private final AppUtils appUtils;

    @Override
    public UserDTO findByUsername() {
        String username = appUtils.getAuthentication().getPrincipal().toString();
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
//                .role(roleRepository.findAllByName(RoleType.VIEWER.name()).get(0))
                .role(roleRepository.findRoleByName("VIEWER"))
                .build();


        User user = userDtoMapper.map(userDto);

        if (userRepository.findById(user.getUsername()).isEmpty()) {
            add(user);
        }
    }


}