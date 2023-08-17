package com.fontana.backend.template.service;


import com.fontana.backend.template.entity.Template;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TemplateService {
    void addTemplate(Template template);

    void deleteTemplate(Template template);

    void deleteTemplateById(Integer templateId);

    void updateTemplate(Template template);

    Template[] getTemplatesByUsername(String username);

    int getFavouriteCount(Template template);

    boolean isFavouritedByOwner(Template template);

    Template getTemplateById(Integer templateId);

    Template[] getAllPublicTemplates();

    List<Template> getTemplatesByUsernamePaginated(String username, Pageable pageable);
}
