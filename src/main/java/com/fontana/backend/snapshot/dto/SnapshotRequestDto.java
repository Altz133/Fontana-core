package com.fontana.backend.snapshot.dto;

import com.fontana.backend.devices.dto.DeviceDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnapshotRequestDto {
    @NotBlank
    private Integer id;
    @NotBlank
    private Integer snapshot_index;
    @NotBlank
    private Integer template_id;
    private List<DeviceDTO> data;

}
