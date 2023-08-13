package com.fontana.backend.snapshot;

public interface SnapshotService {
    void addSnapshot(Snapshot snapshot);

    void deleteSnapshot(Snapshot snapshot);

    void deleteSnapshotById(Integer snapshotId);

    void updateSnapshot(Snapshot snapshot);

    byte[] getDmxDataArray(Snapshot snapshot);

    byte[] getDmxDataArrayById(Integer snapshotId);

    Snapshot[] getSnapshotsByUsername(String username);
}