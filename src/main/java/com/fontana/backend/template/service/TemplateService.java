package com.fontana.backend.template.service;


import com.fontana.backend.template.dto.TemplateDto;
import com.fontana.backend.template.entity.Template;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TemplateService {
    void addTemplate(TemplateDto templateDto);

    void deleteTemplate(Template template);

    void deleteTemplateById(Integer templateId);

    void updateTemplate(Template template);

    List<Template> getTemplatesByUsername(String username);

    int getFavouriteCount(Template template);

    boolean isFavouritedByUser(Template template, String username);

    Template getTemplateById(Integer templateId);

    List<Template> getAllPublicTemplatesByName(String name, Pageable pageable);

    List<Template> getTemplatesByUsernamePaginated(String username, Pageable pageable);

    int getDurationFromTemplate(Template template);

    int getDurationFromTemplates(List<Template> templates);

    List<Template> getTemplatesByIds(List<Integer> templateIds);
    Integer getHighestTemplateId();
}
