package com.fontana.backend.snapshot.service;

import com.fontana.backend.snapshot.entity.Snapshot;

public interface SnapshotService {
    void addSnapshot(Snapshot snapshot);

    void deleteSnapshot(Snapshot snapshot);

    void deleteSnapshotById(Integer snapshotId);

    void updateSnapshot(Snapshot snapshot);

    byte[] getDmxDataArray(Snapshot snapshot);

    byte[] getDmxDataArrayBySnapshotId(Integer snapshotId);

    Snapshot[] getSnapshotsByUsername(String username);

    Snapshot getSnapshotById(Integer snapshotId);
}