package com.fontana.backend.snapshot.service;

import com.fontana.backend.snapshot.entity.Snapshot;
import org.springframework.stereotype.Service;

@Service
public class SnapshotService {

    public void updateSnapshot(Snapshot snapshot, Integer id, byte value) {
        snapshot.setId(id);
        snapshot.setValue(value);
    }
}