package com.fontana.backend.domain.devices;

import com.fontana.backend.domain.devices.DevicesType.DevicesType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Devices {

    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private DevicesType type;

    public Devices() {
    }

}
