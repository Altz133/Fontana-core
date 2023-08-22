package com.fontana.backend.session.repository;

import com.fontana.backend.session.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    @Query("SELECT session FROM Session session ORDER BY session.id DESC")
    List<Session> findAllInReversedOrder();
}
