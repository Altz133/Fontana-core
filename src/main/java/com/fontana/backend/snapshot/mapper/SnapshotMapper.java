package com.fontana.backend.snapshot.mapper;

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

    public List<Snapshot> map(List<SnapshotRequestDto> snapshotRequestDto, Integer highestId){
        List<Snapshot> snapList= new ArrayList<>();
        int i=0;
        for(SnapshotRequestDto snapshot : snapshotRequestDto){
            snapList.add(mapSnapshotFromTemplate(snapshot,highestId,i));
            i++;
        }

        return snapList;
    }
    private Snapshot mapSnapshotFromTemplate(SnapshotRequestDto snapshotRequestDto, Integer highestId, int order){
        byte[] snapshotData = new byte[512];
        for(int  i =0 ; i < snapshotRequestDto.getData().size() ; i++){
            snapshotData = snapshotDataFactory.createData(snapshotRequestDto.getData().get(i), snapshotData);
        }

        Snapshot snapshot = new Snapshot();
        snapshot.setTemplate_id(highestId);
        snapshot.setSnapshot_index(order);
        snapshot.setData(snapshotData);

        return snapshot;
    }



}
