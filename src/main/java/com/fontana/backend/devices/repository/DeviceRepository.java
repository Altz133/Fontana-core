package com.fontana.backend.devices.repository;

import com.fontana.backend.devices.entity.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
}
