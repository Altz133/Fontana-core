package com.fontana.backend.dmxHandler.currentStateDTO.service;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CurrentStateDTOServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private CurrentStateDTOService currentStateDTOService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDevice() {
        int deviceId = 1;
        Device device = new Device();
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        Optional<Device> result = currentStateDTOService.getDevice(deviceId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(device);
    }

    @Test
    public void testGetDevices() {
        List<Device> devices = new ArrayList<>();
        devices.add(new Device());
        devices.add(new Device());

        when(deviceRepository.findAll()).thenReturn(devices);

        List<Device> result = currentStateDTOService.getDevices();

        assertThat(result).hasSize(2);
    }

}
