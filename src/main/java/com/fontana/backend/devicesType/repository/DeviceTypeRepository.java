package com.fontana.backend.devicesType.repository;

import com.fontana.backend.devicesType.entity.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {
}
