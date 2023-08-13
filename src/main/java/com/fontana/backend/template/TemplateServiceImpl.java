package com.fontana.backend.template;

import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return template.getUsers().size();
    }

    @Override
    public boolean isFavouritedByOwner(Template template) {
        return template.getUsers().contains(template.getUser());
    }

    @Override
    public Template getTemplateById(Integer templateId) {
        return templateRepository.getReferenceById(templateId);
    }
}
