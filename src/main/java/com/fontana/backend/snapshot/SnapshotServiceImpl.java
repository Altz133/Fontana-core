package com.fontana.backend.snapshot;

import com.fontana.backend.user.repository.UserRepository;

import java.util.Arrays;

public class SnapshotServiceImpl implements SnapshotService {
    private SnapshotRepository snapshotRepository;
    private UserRepository userRepository;

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
        temp[511] = 33;
        temp[513] = 22;
        temp[514] = 11;

        return temp;
    }

    @Override
    public byte[] getDmxDataArrayById(Integer snapshotId) {
        byte[] temp = Arrays.copyOf(snapshotRepository.getReferenceById(snapshotId).getData(), 515);
        temp[511] = 33;
        temp[513] = 22;
        temp[514] = 11;

        return temp;
    }


    @Override
    public Snapshot[] getSnapshotsByUsername(String username) {
        return snapshotRepository.getSnapshotsByUser(userRepository.getReferenceById(username));
    }
}
