package com.fontana.backend.devices.entity;

import com.fontana.backend.devicetype.entity.DeviceType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Device {

    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private DeviceType type;

    public Device() {
    }

}
