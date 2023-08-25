package com.fontana.backend.snapshot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnapshotRequestDto {
    @NotBlank
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private Integer snapshot_index;
    @NotBlank
    private Integer template_id;
    private byte[] data;
}
