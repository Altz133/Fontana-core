package com.fontana.backend.template.service;

import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.entity.TemplateStatus;
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

    private final int SEQUENCES_PER_SECOND = 4;

    @Override
    public void addTemplate(Template template) {
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
    public Template getTemplateById(Integer templateId) {
        return templateRepository.getReferenceById(templateId);
    }

    @Override
    public List<Template> getAllPublicTemplatesByName(String name, Pageable pageable) {
        return templateRepository.getAllByStatusAndNameContainingOrderByUpdatedAtDesc(TemplateStatus.PUBLIC, name, pageable);
    }

    @Override
    public List<Template> getTemplatesByUsernameAndStatusNotPaginated(String username, TemplateStatus status, Pageable pageable) {
        return templateRepository.getTemplatesByUserAndStatusNotOrderByUpdatedAt(userRepository.getReferenceById(username), status, pageable);
    }

    @Override
    public List<Template> getTemplatesByUsernameAndStatusPaginated(String username, TemplateStatus status, Pageable pageable) {
        return templateRepository.getTemplatesByUserAndStatusOrderByUpdatedAt(userRepository.getReferenceById(username), status, pageable);
    }

    @Override
    public int getDurationFromTemplate(Template template) {
        return (int) Math.ceil((double) template.getSnapshotsSequence().size() / SEQUENCES_PER_SECOND);
    }

    @Override
    public int getDurationFromTemplates(List<Template> templates) {
        int sum = 0;
        for (Template t : templates) {
            sum += t.getSnapshotsSequence().size();
        }

        return (int) Math.ceil((double) sum / SEQUENCES_PER_SECOND);
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
