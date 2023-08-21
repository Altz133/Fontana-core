package com.fontana.backend.template.controller;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.service.TemplatePaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(TEMPLATE)
@RequiredArgsConstructor
public class TemplateController {
    private final TemplatePaginationService templatePaginationService;

    @GetMapping(TEMPLATE_MY_TEMPLATES_PAGINATION)
    public List<TemplateCardDto> getMyTemplates(@RequestParam String username, @RequestParam int page, @RequestParam int size) {
        return templatePaginationService.getMyTemplatesPaginated(username, page, size);
    }

    @GetMapping(TEMPLATE_PUBLIC_TEMPLATES_PAGINATION)
    public List<TemplateCardDto> getPublicTemplates(@RequestParam String name, @RequestParam int page, @RequestParam int size) {
        return templatePaginationService.getPublicTemplatesPaginated(name, page, size);
    }
}
