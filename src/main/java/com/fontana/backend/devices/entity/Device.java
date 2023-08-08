package com.fontana.backend.devices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "devices")
public class Device {

    @Id
    private Integer id;
    private String name;
    private int[] addresses;
    private String type;

}
