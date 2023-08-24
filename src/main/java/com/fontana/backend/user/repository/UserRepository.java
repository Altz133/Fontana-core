package com.fontana.backend.user.repository;

import com.fontana.backend.role.entity.Role;
import com.fontana.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByRole(Role role);

    Optional<User> findByUsername(String username);
}
