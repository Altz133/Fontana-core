package com.fontana.backend.log.repository;

import com.fontana.backend.log.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LogRepository extends JpaRepository<Log, Integer> {

    Page<Log> findAll(Pageable pageable);

    Page<Log> findAllByUsername(String username, Pageable pageable);

    Page<Log> findAllBySessionId(int sessionId, Pageable pageable);
}
