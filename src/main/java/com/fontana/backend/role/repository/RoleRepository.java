package com.fontana.backend.role.repository;

import com.fontana.backend.role.entity.Role;
import com.fontana.backend.role.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAllByRoleType(RoleType roleType);
}
