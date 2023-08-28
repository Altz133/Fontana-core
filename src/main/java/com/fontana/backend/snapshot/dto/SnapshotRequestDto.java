package com.fontana.backend.snapshot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnapshotRequestDto {
    private Integer id;
//    @NotBlank
//    private Integer snapshot_index;
//    @NotBlank
//    private Integer template_id;
//    private Integer duration;

    private DevicesRequestDto devices;

}
