package com.fontana.backend.snapshot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnapshotRequestDto {

    private Integer duration;

    private DevicesRequestDto devices;

}
