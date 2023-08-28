package com.fontana.backend.snapshot.mapper;

import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.snapshot.dto.SnapshotRequestDto;
import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.snapshot.factory.SnapshotDataFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SnapshotMapper {
    private final SnapshotDataFactory snapshotDataFactory;
    private final DeviceRepository deviceRepository;


    public List<Snapshot> map(List<SnapshotRequestDto> snapshotRequestDto){
        List<Snapshot> snapList= new ArrayList<>();
        for(SnapshotRequestDto snapshot : snapshotRequestDto){
            snapList.add(mapSnapshotFromTemplate(snapshot));

        }

        return snapList;
    }
    private Snapshot mapSnapshotFromTemplate(SnapshotRequestDto snapshotRequestDto){
        byte[] snapshotData = new byte[512];

        snapshotData = snapshotDataFactory.createData(snapshotRequestDto.getDevices(), snapshotData );
        Snapshot snapshot = new Snapshot();
        snapshot.setDuration(snapshotRequestDto.getDuration());
        snapshot.setData(snapshotData);

        return snapshot;
    }



}
