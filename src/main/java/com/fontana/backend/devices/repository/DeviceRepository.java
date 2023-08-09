package com.fontana.backend.devices.repository;

import com.fontana.backend.devices.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Device findByName(String name);

    List<Device> findByType(String type);

    List<Device> findByAddress(int[] addresses);

}
