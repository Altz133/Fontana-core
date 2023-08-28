package com.fontana.backend.snapshot.repository;

import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotRepository extends JpaRepository<Snapshot, Integer> {

    Snapshot[] getSnapshotsByUser(User user);
}