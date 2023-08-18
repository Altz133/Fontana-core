package com.fontana.backend.template.controller;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.mapper.TemplateCardDtoMapper;
import com.fontana.backend.template.service.TemplateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return templateService.getMyTemplates(username);
    }

    @GetMapping(TEMPLATE_MY_TEMPLATES_CARDS_PAGINATION)
    public List<TemplateCardDto> getMyTemplates(@RequestParam String username, @PathVariable int page, @PathVariable int size) {
        return templateService.getMyTemplatesPaginated(username, page, size);
    }

    @GetMapping(TEMPLATE_PUBLIC_TEMPLATES_CARDS)
    public List<TemplateCardDto> getPublicTemplates() {
        return templateService.getPublicTemplates();
    }
}
