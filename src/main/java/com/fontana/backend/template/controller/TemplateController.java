package com.fontana.backend.template.controller;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.mapper.TemplateCardDtoMapper;
import com.fontana.backend.template.service.TemplateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(TEMPLATE_MY_TEMPLATES_CARDS_PAGINATION)
    public List<TemplateCardDto> getMyTemplates(@RequestParam String username, @PathVariable int page, @PathVariable int size) {
        List<TemplateCardDto> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);

        for (Template t : templateService.getTemplatesByUsernamePaginated(username, pageable)) {
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
