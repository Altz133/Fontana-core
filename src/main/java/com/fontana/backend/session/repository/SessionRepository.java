package com.fontana.backend.session.repository;

import com.fontana.backend.session.entity.Session;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    @Query("SELECT session FROM Session session WHERE session.openedTime > :cutoffDate AND session.username != :username ORDER BY session.id DESC")
    List<Session> findAllInReversedOrderAfterDate(
            @Param("cutoffDate") LocalDateTime cutoffDate,
            @Param("username") String username,
            Pageable pageable);

    @Query("SELECT session FROM Session session ORDER BY session.id DESC")
    List<Session> findLatestSessions(Pageable pageable);
}
