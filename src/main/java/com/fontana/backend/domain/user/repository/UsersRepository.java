package com.fontana.backend.domain.user.repository;

import com.fontana.backend.domain.user.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, String> {

}
