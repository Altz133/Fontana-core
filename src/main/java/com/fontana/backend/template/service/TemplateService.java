package com.fontana.backend.template.service;


import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.entity.Template;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TemplateService {
    void addTemplate(Template template);

    void deleteTemplate(Template template);

    void deleteTemplateById(Integer templateId);

    void updateTemplate(Template template);

    List<Template> getTemplatesByUsername(String username);

    int getFavouriteCount(Template template);

    boolean isFavouritedByOwner(Template template);

    Template getTemplateById(Integer templateId);

    List<Template> getAllPublicTemplates();

    List<Template> getTemplatesByUsernamePaginated(String username, Pageable pageable);

    int getDurationFromTemplate(Template template);

    int getDurationFromTemplates(List<Template> templates);

    List<TemplateCardDto> getMyTemplates(String username);

    List<TemplateCardDto> getMyTemplatesPaginated(String username, int page, int size);

    List<TemplateCardDto> getPublicTemplates();
}
