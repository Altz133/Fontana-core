package com.fontana.backend.device.repository;

import com.fontana.backend.device.entity.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
}
