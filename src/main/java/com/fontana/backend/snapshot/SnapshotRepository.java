package com.fontana.backend.snapshot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotRepository extends JpaRepository<Snapshot, Integer> {
}
