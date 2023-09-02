package com.fontana.backend.devices.repository;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.entity.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Device findByName(String name);

    List<Device> findByType(DeviceType type);

    List<Device> findByAddress(int[] addresses);

    @Query(value = "SELECT * FROM Device d WHERE ?1 = ANY (d.address)", nativeQuery = true)
    Device findContainedAddress(int addressContained);

}