package com.fontana.backend.template.dto;

import com.fontana.backend.template.entity.TemplateStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateCardDto {

    private String name;

    private String ownerUsername;

    private Integer favourites;

    private boolean isFavouritedByOwner;

    private Timestamp updatedAt;

    private TemplateStatus status;
}