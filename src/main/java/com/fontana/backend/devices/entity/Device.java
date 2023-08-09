package com.fontana.backend.devices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "device")
public class Device {

    @Id
    @NotNull
    @Min(value = 1)
    @Max(value = 512)
    private Integer id;
    @NotEmpty
    private String name;
    private int[] address;
    @NotEmpty
    private String type;

}
