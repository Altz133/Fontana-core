package com.fontana.backend.role.repository;

import com.fontana.backend.role.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends CrudRepository<Role, Integer> {
}
