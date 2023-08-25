package com.fontana.backend.snapshot.service;

import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.snapshot.repository.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class SnapshotServiceImpl implements SnapshotService {
    private final SnapshotRepository snapshotRepository;

    @Override
    public void addSnapshot(Snapshot snapshot) {
        snapshotRepository.save(snapshot);
    }

    @Override
    public void deleteSnapshot(Snapshot snapshot) {
        snapshotRepository.delete(snapshot);
    }

    @Override
    public void deleteSnapshotById(Integer snapshotId) {
        snapshotRepository.deleteById(snapshotId);
    }

    @Override
    public void updateSnapshot(Snapshot snapshot) {
        snapshotRepository.save(snapshot);
    }

    @Override
    public byte[] getDmxDataArray(Snapshot snapshot) {
        byte[] temp = Arrays.copyOf(snapshot.getData(), 515);
        temp[512] = 33;
        temp[513] = 22;
        temp[514] = 11;

        return temp;
    }

    @Override
    public byte[] getDmxDataArrayBySnapshotId(Integer snapshotId) {
        byte[] temp = Arrays.copyOf(snapshotRepository.getReferenceById(snapshotId).getData(), 515);
        temp[512] = 33;
        temp[513] = 22;
        temp[514] = 11;

        return temp;
    }

}
