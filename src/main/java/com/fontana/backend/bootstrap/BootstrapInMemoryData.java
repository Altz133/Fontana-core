package com.fontana.backend.bootstrap;

import com.fontana.backend.role.entity.Role;
import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.role.repository.RoleRepository;
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

    @Transactional
    @Override
    public void run(String... args) {
        loadTestRoles();
    }

    private void loadTestRoles() {
        if (roleRepository.findAll().size() == 0) {
            Role admin = Role.builder()
                    .roleType(RoleType.ADMIN)
                    .users(null)
                    .build();

            Role operator = Role.builder()
                    .roleType(RoleType.OPERATOR)
                    .users(null)
                    .build();

            Role viewer = Role.builder()
                    .roleType(RoleType.VIEWER)
                    .users(null)
                    .build();

            roleRepository.save(admin);
            roleRepository.save(operator);
            roleRepository.save(viewer);
        }
    }
}
