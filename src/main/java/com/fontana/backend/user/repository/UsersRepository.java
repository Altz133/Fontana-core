package com.fontana.backend.user.repository;

import com.fontana.backend.user.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, String> {

}
