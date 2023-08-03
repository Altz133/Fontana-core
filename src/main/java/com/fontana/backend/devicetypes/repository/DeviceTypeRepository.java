package com.fontana.backend.devicetypes.repository;

import com.fontana.backend.devicetypes.entity.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {
}
