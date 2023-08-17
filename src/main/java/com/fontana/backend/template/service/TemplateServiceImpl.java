package com.fontana.backend.template.service;

import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.entity.TemplateStatus;
import com.fontana.backend.template.repository.TemplateRepository;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

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
    public Template[] getTemplatesByUsername(String username) {
        return templateRepository.getTemplatesByUser(userRepository.getReferenceById(username));
    }

    @Override
    public int getFavouriteCount(Template template) {
        return template.getUsersFavourited().size();
    }

    @Override
    public boolean isFavouritedByOwner(Template template) {
        return template.getUsersFavourited().contains(template.getUser());
    }

    @Override
    public Template getTemplateById(Integer templateId) {
        return templateRepository.getReferenceById(templateId);
    }

    @Override
    public Template[] getAllPublicTemplates() {
        return templateRepository.getAllByStatus(TemplateStatus.PUBLIC);
    }

    @Override
    public List<Template> getTemplatesByUsernamePaginated(String username, Pageable pageable) {
        return templateRepository.getTemplatesByUser(userRepository.getReferenceById(username), pageable);
    }
}
