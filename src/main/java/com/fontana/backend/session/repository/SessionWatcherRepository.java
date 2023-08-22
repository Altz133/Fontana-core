package com.fontana.backend.session.repository;

import com.fontana.backend.session.entity.SessionWatcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionWatcherRepository extends JpaRepository<SessionWatcher, Integer> {
}
