package com.fontana.backend.devicetype.repository;

import com.fontana.backend.devicetype.entity.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {
}
