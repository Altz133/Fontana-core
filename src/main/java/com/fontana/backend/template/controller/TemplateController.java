package com.fontana.backend.template.controller;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.dto.TemplateDto;
import com.fontana.backend.template.service.TemplatePaginationService;
import com.fontana.backend.template.service.TemplateService;
import com.fontana.backend.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(TEMPLATE)
@RequiredArgsConstructor
public class TemplateController {
    private final TemplatePaginationService templatePaginationService;
    private final AuthUtils authUtils;
    private final TemplateService templateService;

    @GetMapping(TEMPLATE_MY_TEMPLATES_PAGINATION)
    public Page<TemplateCardDto> getMyTemplates(@RequestParam int page, @RequestParam int size) {
        String username = authUtils.getAuthentication().getPrincipal().toString();
        return templatePaginationService.getMyTemplatesPaginated(username, page, size);
    }

    @GetMapping(TEMPLATE_PUBLIC_TEMPLATES_PAGINATION)
    public Page<TemplateCardDto> getPublicTemplates(@RequestParam String name, @RequestParam int page, @RequestParam int size) {
        return templatePaginationService.getPublicTemplatesPaginated(name, page, size);
    }

    @GetMapping(TEMPLATE_MY_TEMPLATES_SNIPPET)
    public List<TemplateCardDto> getMyTemplatesSnippet() {
        String username = authUtils.getAuthentication().getPrincipal().toString();
        return templatePaginationService.getMyTemplatesSnippets(username, 0, 3);
    }

    @GetMapping(TEMPLATE_DRAFT_TEMPLATES_SNIPPET)
    public List<TemplateCardDto> getDraftTemplatesSnippet() {
        String username = authUtils.getAuthentication().getPrincipal().toString();
        return templatePaginationService.getDraftTemplatesSnippets(username, 0, 4);
    }

    @PostMapping()
    public ResponseEntity<?> addTemplate(@RequestBody TemplateDto templateDto) {

        this.templateService.addTemplate(templateDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(TEMPLATE_DELETE)
    public ResponseEntity<?> deleteTemplate(@PathVariable Integer id) {
        templateService.hideTemplate(id);
        return ResponseEntity.ok().build();
    }
}
