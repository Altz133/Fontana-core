package com.fontana.backend.devices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Device {

    @Id
    @NotNull
    @Min(value = 0)
    @Max(value = 511)
    private Integer id;

    @NotEmpty
    private String name;

    private int[] address;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private DeviceType type;
}
