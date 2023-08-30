package com.fontana.backend.template.service;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.entity.TemplateStatus;
import com.fontana.backend.template.mapper.TemplateCardDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplatePaginationService {
    private final TemplateService templateService;
    private final TemplateCardDtoMapper templateCardDtoMapper;

    public Page<TemplateCardDto> getMyTemplatesPaginated(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Template> list = templateService.getMyTemplatesByUsername(username, pageable);
        return list.map(templateCardDtoMapper::TemplateToTemplateCardDto);
    }

    public Page<TemplateCardDto> getPublicTemplatesPaginated(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Template> list = templateService.getAllPublicTemplatesByName(name, pageable);
        return list.map(templateCardDtoMapper::TemplateToTemplateCardDto);
    }

    public List<TemplateCardDto> getMyTemplatesSnippets(String username, int page, int size) {
        return templateService.getTemplatesByUsernameAndWithoutStatusSortedByUpdate(username, TemplateStatus.DRAFT, PageRequest.of(page, size)).stream().map(templateCardDtoMapper::TemplateToTemplateCardDto).toList();
    }

    public List<TemplateCardDto> getDraftTemplatesSnippets(String username, int page, int size) {
        return templateService.getTemplatesByUsernameAndStatusSortedByUpdate(username, TemplateStatus.DRAFT, PageRequest.of(page, size)).stream().map(templateCardDtoMapper::TemplateToTemplateCardDto).toList();
    }
}
