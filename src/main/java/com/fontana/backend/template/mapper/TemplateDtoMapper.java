package com.fontana.backend.template.mapper;

import com.fontana.backend.snapshot.service.SnapshotService;
import com.fontana.backend.template.dto.TemplateDto;
import com.fontana.backend.template.entity.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class TemplateDtoMapper {
    private final SnapshotService snapshotService;
    public Template mapNew(TemplateDto templateDto){
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

        return Template.builder()
                .name(templateDto.getName())
                .status(templateDto.getStatus())
                .updatedAt(updatedAt)
                .createdAt(templateDto.getCreated_at())
                //TODO
                //Obgadać z nikodemem snapshoty na froncie bo jest dysonans
                //jak to widzisz na codereview to zapomniałem to usunąć daj mi znać elo
//                .snapshotsSequence()
                .build();
    }
}
