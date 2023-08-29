package com.fontana.backend.template.mapper;

import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.snapshot.mapper.SnapshotMapper;
import com.fontana.backend.snapshot.service.SnapshotService;
import com.fontana.backend.template.dto.TemplateDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.repository.TemplateRepository;
import com.fontana.backend.template.service.TemplateService;
import com.fontana.backend.user.entity.User;
import com.fontana.backend.user.repository.UserRepository;
import com.fontana.backend.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateDtoMapper {
    private final SnapshotMapper snapshotMapper;
    private final AuthUtils authUtils;
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;

    public Template mapNew(TemplateDto templateDto){
        List<Snapshot> snapshotList = snapshotMapper.map(templateDto.getSnapshots());
        User user = userRepository.findByUsername(authUtils.getAuthentication().getPrincipal().toString()).orElseThrow();
        return Template.builder()
                .name(templateDto.getName())
                .user(user)
                .status(templateDto.getStatus())
                .snapshotsSequence(snapshotList)
                .build();
    }
}
