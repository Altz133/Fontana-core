package com.fontana.backend.template.service;

import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.template.dto.TemplateDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.entity.TemplateStatus;
import com.fontana.backend.template.mapper.TemplateDtoMapper;
import com.fontana.backend.template.repository.TemplateRepository;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final TemplateDtoMapper templateDtoMapper;
    private final int SEQUENCES_PER_SECOND = 4;

    public void addTemplate(TemplateDto templateDto) {
        templateRepository.save(templateDtoMapper.mapNew(templateDto));

    }

    public void deleteTemplate(Template template) {
        templateRepository.delete(template);
    }

    public void deleteTemplateById(Integer templateId) {
        templateRepository.delete(templateRepository.getReferenceById(templateId));
    }

    public void updateTemplate(Template template) {
        templateRepository.save(template);
    }

    public Template getTemplateById(Integer templateId) {
        return templateRepository.getReferenceById(templateId);
    }

    //my templates
    public Page<Template> getMyTemplatesByUsername(String username, Pageable pageable) {
        return templateRepository.getTemplatesByStatusNotAndUserOrderByNameAsc(TemplateStatus.HIDDEN, userRepository.getReferenceById(username), pageable);
    }

    //public templates with search
    public Page<Template> getAllPublicTemplatesByName(String name, Pageable pageable) {
        return templateRepository.getTemplatesByStatusAndNameContainsOrderByNameAsc(TemplateStatus.PUBLIC, name, pageable);
    }

    //snippet my templates
    public List<Template> getTemplatesByUsernameAndWithoutStatusSortedByUpdate(String username, Pageable pageable) {
        return templateRepository.getTemplatesByUserAndStatusNotAndStatusNotOrderByUpdatedAtDesc(userRepository.getReferenceById(username), TemplateStatus.DRAFT, TemplateStatus.HIDDEN, pageable);
    }

    //snippet editing tool
    public List<Template> getTemplatesByUsernameAndStatusSortedByUpdate(String username, Pageable pageable) {
        return templateRepository.getTemplatesByUserAndStatusOrderByUpdatedAtDesc(userRepository.getReferenceById(username), TemplateStatus.DRAFT, pageable);
    }

    //delete template
    public void hideTemplate(Integer templateId) {
        Optional<Template> template = templateRepository.findById(templateId);

        if (template.isPresent()) {
            template.get().setStatus(TemplateStatus.HIDDEN);
            templateRepository.save(template.get());

        }
    }

    public int getDurationFromTemplate(Template template) {
        return (int) Math.ceil((double) template.getSnapshotsSequence().stream().map(Snapshot::getDuration).mapToInt(o -> o).sum() / SEQUENCES_PER_SECOND);
    }

    public int getDurationFromTemplates(List<Template> templates) {
        int sum = 0;
        for (Template t : templates) {
            sum += t.getSnapshotsSequence().stream().map(Snapshot::getDuration).mapToInt(o -> o).sum();
        }

        return (int) Math.ceil((double) sum / SEQUENCES_PER_SECOND);
    }

    public List<Template> getTemplatesByIds(List<Integer> templateIds) {
        List<Template> templates = new ArrayList<>();

        for (Integer id : templateIds) {
            templates.add(templateRepository.getReferenceById(id));
        }
        return templates;
    }
}
