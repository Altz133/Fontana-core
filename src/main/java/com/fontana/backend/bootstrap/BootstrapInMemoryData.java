package com.fontana.backend.bootstrap;

import com.fontana.backend.log.entity.Log;
import com.fontana.backend.log.repository.LogRepository;
import com.fontana.backend.role.entity.Role;
import com.fontana.backend.role.repository.RoleRepository;
import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.session.entity.Session;
import com.fontana.backend.session.repository.SessionRepository;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    private final LogRepository logRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    @Override
    public void run(String... args) {
        loadTestRoles();
        loadTestUsers();
        loadTestSession();
        loadTestLogs();
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

    private void loadTestSession() {
        Session session = Session.builder()
                .username("fontanna_operator")
                .openedTime(LocalDateTime.now())
                .closedTime(null)
                .expirationTime(LocalDateTime.now().plusMinutes(5))
                .logs(new ArrayList<>())
                .isForcedToClose(false)
                .isAutoClosed(false)
                .build();

        sessionRepository.save(session);
    }

    private void loadTestLogs() {
        Log first = Log.builder()
                .deviceValue((short) 48)
                .executedAt(LocalDateTime.now())
                .user(userRepository.findAll().get(0))
                .sessionId(sessionRepository.findAll().get(0).getId())
                .build();

        logRepository.save(first);
    }
}
