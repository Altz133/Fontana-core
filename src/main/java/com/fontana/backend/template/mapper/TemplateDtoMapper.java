package com.fontana.backend.template.mapper;

import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.snapshot.mapper.SnapshotMapper;
import com.fontana.backend.snapshot.service.SnapshotService;
import com.fontana.backend.template.dto.TemplateDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateDtoMapper {
    private final SnapshotService snapshotService;
    private final SnapshotMapper snapshotMapper;
    private final TemplateService templateService;
    public Template mapNew(TemplateDto templateDto){
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
        List<Snapshot> snapshotList = snapshotMapper.map(templateDto.getSnapshots());

        return Template.builder()
                .name(templateDto.getName())
                .status(templateDto.getStatus())
                .updatedAt(updatedAt)
                .createdAt(templateDto.getCreated_at())
                .snapshotsSequence(snapshotList)
                .build();
    }
}
