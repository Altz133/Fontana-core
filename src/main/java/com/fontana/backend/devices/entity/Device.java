package com.fontana.backend.devices.entity;

import com.fontana.backend.devicesType.entity.DeviceType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="devices")
public class Device {

    @Id
    private Integer id;
    private String name;
    private byte[] addresses;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private DeviceType type;

    public Device() {
    }


}
