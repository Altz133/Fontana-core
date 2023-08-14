package com.fontana.backend.snapshot;

import com.fontana.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotRepository extends JpaRepository<Snapshot, Integer> {

    Snapshot[] getSnapshotsByUser(User user);
}