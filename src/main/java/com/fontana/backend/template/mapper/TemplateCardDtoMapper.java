package com.fontana.backend.template.mapper;

import com.fontana.backend.template.dto.TemplateCardDto;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.service.TemplateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateCardDtoMapper {

    private final TemplateServiceImpl templateService;

    public TemplateCardDto TemplateToTemplateCardDto(Template template) {
        return TemplateCardDto.builder()
                .id(template.getId())
                .name(template.getName())
                .ownerUsername(template.getUser().getUsername())
                .favourites(templateService.getFavouriteCount(template))
                .isFavouritedByOwner(templateService.isFavouritedByOwner(template))
                .updatedAt(template.getUpdatedAt())
                .status(template.getStatus())
                .length(template.getSnapshotsSequence().size() / 4) //jeden snapshot co 0.25s
                .build();
    }
}
