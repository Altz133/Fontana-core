package com.fontana.backend.snapshot.repository;

import com.fontana.backend.snapshot.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotRepository extends JpaRepository<Snapshot, Integer> {


}