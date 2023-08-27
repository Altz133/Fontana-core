package com.fontana.backend.template.service;

import com.fontana.backend.template.dto.TemplateDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.entity.TemplateStatus;
import com.fontana.backend.template.mapper.TemplateDtoMapper;
import com.fontana.backend.template.repository.TemplateRepository;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final TemplateDtoMapper templateDtoMapper;

    private final int SEQUENCES_PER_SECOND = 4;

    @Override
    public void addTemplate(TemplateDto templateDto) {
        Template template = templateRepository.save(templateDtoMapper.mapNew(templateDto));
        templateRepository.save(template);

    }

    @Override
    public void deleteTemplate(Template template) {
        templateRepository.delete(template);
    }

    @Override
    public void deleteTemplateById(Integer templateId) {
        templateRepository.delete(templateRepository.getReferenceById(templateId));
    }

    @Override
    public void updateTemplate(Template template) {
        templateRepository.save(template);
    }

    @Override
    public List<Template> getTemplatesByUsername(String username) {
        return templateRepository.getTemplatesByUser(userRepository.getReferenceById(username));
    }

    @Override
    public int getFavouriteCount(Template template) {
        return template.getUsersFavourited().size();
    }

    @Override
    public boolean isFavouritedByUser(Template template, String username) {

        return template.getUsersFavourited().contains(userRepository.getReferenceById(username));
    }

    @Override
    public Template getTemplateById(Integer templateId) {
        return templateRepository.getReferenceById(templateId);
    }

    @Override
    public List<Template> getAllPublicTemplatesByName(String name, Pageable pageable) {
        return templateRepository.getAllByStatusAndNameContaining(TemplateStatus.PUBLIC, name, pageable);
    }

    @Override
    public List<Template> getTemplatesByUsernamePaginated(String username, Pageable pageable) {
        return templateRepository.getTemplatesByUser(userRepository.getReferenceById(username), pageable);
    }

    @Override
    public int getDurationFromTemplate(Template template) {
        return template.getSnapshotsSequence().size() / SEQUENCES_PER_SECOND;
    }
    public Integer getHighestTemplateId(){
        return templateRepository.findTopByOrderByIdDesc();
    }

    @Override
    public int getDurationFromTemplates(List<Template> templates) {
        int sum = 0;
        for (Template t : templates) {
            sum += t.getSnapshotsSequence().size();
        }

        return sum / SEQUENCES_PER_SECOND;
    }

    @Override
    public List<Template> getTemplatesByIds(List<Integer> templateIds) {
        List<Template> templates = new ArrayList<>();

        for (Integer id : templateIds) {
            templates.add(templateRepository.getReferenceById(id));
        }
        return templates;
    }
}
