package com.fontana.backend.template.service;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.entity.TemplateStatus;
import com.fontana.backend.template.mapper.TemplateCardDtoMapper;
import com.fontana.backend.template.repository.TemplateRepository;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final TemplateCardDtoMapper templateCardDtoMapper;

    private final int sequencesPerSecond = 4;

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
    public List<Template> getAllPublicTemplates() {
        return templateRepository.getAllByStatus(TemplateStatus.PUBLIC);
    }

    @Override
    public List<Template> getTemplatesByUsernamePaginated(String username, Pageable pageable) {
        return templateRepository.getTemplatesByUser(userRepository.getReferenceById(username), pageable);
    }

    @Override
    public int getDurationFromTemplate(Template template) {
        return template.getSnapshotsSequence().size() / sequencesPerSecond;
    }

    @Override
    public int getDurationFromTemplates(List<Template> templates) {
        int sum = 0;
        for (Template t : templates) {
            sum += t.getSnapshotsSequence().size();
        }

        return sum / sequencesPerSecond;
    }

    @Override
    public List<TemplateCardDto> getMyTemplates(String username) {
        List<TemplateCardDto> list = new ArrayList<>();

        for (Template t : getTemplatesByUsername(username)) {
            list.add(templateCardDtoMapper.TemplateToTemplateCardDto(t));
        }

        return list;
    }

    @Override
    public List<TemplateCardDto> getMyTemplatesPaginated(String username, int page, int size) {
        List<TemplateCardDto> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);

        for (Template t : getTemplatesByUsernamePaginated(username, pageable)) {
            list.add(templateCardDtoMapper.TemplateToTemplateCardDto(t));
        }

        return list;
    }

    @Override
    public List<TemplateCardDto> getPublicTemplates() {
        List<TemplateCardDto> list = new ArrayList<>();

        for (Template t : getAllPublicTemplates()) {
            list.add(templateCardDtoMapper.TemplateToTemplateCardDto(t));
        }

        return list;
    }
}
