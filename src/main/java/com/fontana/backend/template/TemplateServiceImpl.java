package com.fontana.backend.template;

import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

    public void addTemplate(Template template) {
        templateRepository.save(template);
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

    public Template[] getTemplatesByUsername(String username) {
        return templateRepository.getTemplatesByUser(userRepository.getReferenceById(username));
    }

    public int getFavouriteCount(Template template) {
        return template.getUsers().size();
    }

    public boolean isFavouritedByOwner(Template template) {
        return template.getUsers().contains(template.getUser());
    }
}
