package com.fontana.backend.template.controller;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.mapper.TemplateCardDtoMapper;
import com.fontana.backend.template.service.TemplateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(TEMPLATE)
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateServiceImpl templateService;
    private final TemplateCardDtoMapper templateCardDtoMapper;

    @GetMapping(TEMPLATE_MY_TEMPLATES_CARDS)
    public List<TemplateCardDto> getMyTemplates(@RequestParam String username) {
        List<TemplateCardDto> list = new ArrayList<>();

        for (Template t : templateService.getTemplatesByUsername(username)) {
            list.add(templateCardDtoMapper.TemplateToTemplateCardDto(t));
        }

        return list;
    }

    @GetMapping(TEMPLATE_PUBLIC_TEMPLATES_CARDS)
    public List<TemplateCardDto> getPublicTemplates() {
        List<TemplateCardDto> list = new ArrayList<>();

        for (Template t : templateService.getAllPublicTemplates()) {
            list.add(templateCardDtoMapper.TemplateToTemplateCardDto(t));
        }

        return list;
    }
}
