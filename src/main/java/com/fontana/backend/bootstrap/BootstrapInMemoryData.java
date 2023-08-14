package com.fontana.backend.bootstrap;

import com.fontana.backend.role.Role;
import com.fontana.backend.role.RoleRepository;
import com.fontana.backend.role.RoleType;
import com.fontana.backend.user.User;
import com.fontana.backend.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This class allows you to create a bunch of mock data and put them in H2 database on each run, so you always have
 * data to work with.
 */

@Component
@RequiredArgsConstructor
@Profile("h2")
public class BootstrapInMemoryData implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Transactional
    @Override
    public void run(String... args) {
        loadTestRoles();
        loadTestUsers();
    }

    private void loadTestRoles() {
        if (roleRepository.findAll().size() == 0) {
            Role admin = Role.builder()
                    .name(RoleType.ADMIN.name())
                    .users(null)
                    .build();

            Role operator = Role.builder()
                    .name(RoleType.OPERATOR.name())
                    .users(null)
                    .build();

            Role viewer = Role.builder()
                    .name(RoleType.VIEWER.name())
                    .users(null)
                    .build();

            roleRepository.save(admin);
            roleRepository.save(operator);
            roleRepository.save(viewer);
        }
    }

    private void loadTestUsers() {
        User admin = User.builder()
                .username("fontanna_admin")
                .firstName("Fontanna")
                .lastName("Admin")
                .role(roleRepository.findAllByName(RoleType.ADMIN.name()).get(0))
                .build();

        User operator = User.builder()
                .username("fontanna_operator")
                .firstName("Fontanna")
                .lastName("Operator")
                .role(roleRepository.findAllByName(RoleType.OPERATOR.name()).get(0))
                .build();

        userRepository.save(admin);
        userRepository.save(operator);
    }
}
