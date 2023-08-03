package com.fontana.backend.devicetype.entity;

import com.fontana.backend.devices.entity.Device;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DeviceType {
    @Id
    private Integer id;
    @OneToMany(mappedBy = "type")
    private List<Device> listOfDevices = new ArrayList<>();

    public DeviceType() {
    }
}
