package com.fontana.backend.template.controller;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.mapper.TemplateCardDtoMapper;
import com.fontana.backend.template.service.TemplateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateControllerMethods {
    private final TemplateServiceImpl templateService;
    private final TemplateCardDtoMapper templateCardDtoMapper;

    public List<TemplateCardDto> getMyTemplates(String username) {
        List<TemplateCardDto> list = new ArrayList<>();

        for (Template t : templateService.getTemplatesByUsername(username)) {
            list.add(templateCardDtoMapper.TemplateToTemplateCardDto(t));
        }

        return list;
    }

    public List<TemplateCardDto> getMyTemplatesPaginated(String username, int page, int size) {
        List<TemplateCardDto> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);

        for (Template t : templateService.getTemplatesByUsernamePaginated(username, pageable)) {
            list.add(templateCardDtoMapper.TemplateToTemplateCardDto(t));
        }

        return list;
    }

    public List<TemplateCardDto> getPublicTemplates() {
        List<TemplateCardDto> list = new ArrayList<>();

        for (Template t : templateService.getAllPublicTemplates()) {
            list.add(templateCardDtoMapper.TemplateToTemplateCardDto(t));
        }

        return list;
    }
}
