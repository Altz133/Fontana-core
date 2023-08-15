package com.fontana.backend.log.repository;

import com.fontana.backend.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {

    List<Log> findAllByUsername(String username);

    List<Log> findAllBySessionId(int sessionId);
}
