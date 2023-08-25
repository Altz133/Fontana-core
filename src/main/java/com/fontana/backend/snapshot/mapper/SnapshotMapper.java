package com.fontana.backend.snapshot.mapper;

import com.fontana.backend.snapshot.dto.SnapshotRequestDto;
import com.fontana.backend.snapshot.entity.Snapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SnapshotMapper {

    public Snapshot map (SnapshotRequestDto snapshotRequestDto){

        return Snapshot.builder()
                .id(snapshotRequestDto.getId())
                .name(snapshotRequestDto.getName())
                .data(snapshotRequestDto.getData())
                .snapshot_index(snapshotRequestDto.getSnapshot_index())
                .template_id(snapshotRequestDto.getTemplate_id())
                .build();
    }

}
