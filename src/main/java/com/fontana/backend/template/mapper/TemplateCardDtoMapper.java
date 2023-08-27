package com.fontana.backend.template.mapper;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.service.TemplateServiceImpl;
import com.fontana.backend.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateCardDtoMapper {

    private final TemplateServiceImpl templateService;
    private final AuthUtils authUtils;

    public TemplateCardDto TemplateToTemplateCardDto(Template template) {
        String username = authUtils.getAuthentication().getPrincipal().toString();
        return TemplateCardDto.builder()
                .id(template.getId())
                .name(template.getName())
                .ownerUsername(template.getUser().getUsername())
                .favourites(templateService.getFavouriteCount(template))
                .isFavouritedByUser(templateService.isFavouritedByUser(template,username))
                .updatedAt(template.getUpdatedAt())
                .status(template.getStatus())
                .length(templateService.getDurationFromTemplate(template))
                .build();
    }
}
