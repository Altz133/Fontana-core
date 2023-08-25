package com.fontana.backend.template.dto;

import com.fontana.backend.template.entity.TemplateStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateDto {
    private TemplateStatus status;
    private Timestamp created_at;
    private String name;
    private String username;
    private List<Integer> snapshots;

}
