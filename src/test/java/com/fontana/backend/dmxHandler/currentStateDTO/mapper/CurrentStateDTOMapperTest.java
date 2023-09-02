package com.fontana.backend.dmxHandler.currentStateDTO.mapper;

import com.fontana.backend.devices.dto.DeviceDTO;
import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.entity.DeviceType;
import com.fontana.backend.devices.jet.dto.JetDTOGet;
import com.fontana.backend.devices.led.dto.LedDTOGet;
import com.fontana.backend.devices.light.dto.LightDTOGet;
import com.fontana.backend.devices.pump.dto.PumpDTOGet;
import com.fontana.backend.dmxHandler.currentStateDTO.factory.DeviceDTOFactory;
import com.fontana.backend.dmxHandler.currentStateDTO.service.CurrentStateDTOService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class CurrentStateDTOMapperTest {

    @Mock
    private DeviceDTOFactory deviceDTOFactory;

    @Mock
    private CurrentStateDTOService currentStateDTOService;

    @InjectMocks
    private CurrentStateDTOMapper currentStateDTOMapper;

    @Test
    void testDMXtoCurrentStateDTO() {
        byte[] DMXDataArray = new byte[]{1, 2, 3, 4, 5, 6};
        int[] addresses = new int[]{1, 2};


        Device device1 = new Device(1, "Device 1", addresses, DeviceType.LED);
        Device device2 = new Device(2, "Device 2", addresses, DeviceType.JET);

        when(currentStateDTOService.getDevices()).thenReturn(List.of(device1, device2));

        when(deviceDTOFactory.createDeviceDTO(eq(device1), eq(DMXDataArray), eq(addresses)))
                .thenReturn(new LedDTOGet("Test Led",(byte) 100,(byte) 100,(byte) 100,(byte) 100,(byte) 100,(byte) 100));

        when(deviceDTOFactory.createDeviceDTO(eq(device2), eq(DMXDataArray), eq(addresses)))
                .thenReturn(new JetDTOGet("Test Jet",true));

        when(deviceDTOFactory.createDeviceDTO(eq(device2), eq(DMXDataArray), eq(addresses)))
                .thenReturn(new PumpDTOGet("Test Pump",(byte) 100));

        when(deviceDTOFactory.createDeviceDTO(eq(device2), eq(DMXDataArray), eq(addresses)))
                .thenReturn(new LightDTOGet("Test Light",(byte) 100,(byte)120,(byte)130));

        List<DeviceDTO> currentStateDTO = currentStateDTOMapper.DMXtoCurrentStateDTO(DMXDataArray);

        for (int i = 0; i < currentStateDTO.size(); i++) {
            DeviceDTO dto = currentStateDTO.get(i);
            assertThat(dto).isInstanceOfAny(LedDTOGet.class, JetDTOGet.class, PumpDTOGet.class, LightDTOGet.class);

            if (dto instanceof LedDTOGet) {
                assertThat(dto.getName()).isEqualTo("Test Led");
                assertThat(dto.toString()).contains("colorR=100", "colorG=100", "colorB=100", "colorW=100", "power=100", "stroboscopeFrequency=100");
            } else if (dto instanceof JetDTOGet) {
                assertThat(dto.getName()).isEqualTo("Test Jet");
                assertThat(dto.toString()).contains("value=true");
            } else if (dto instanceof PumpDTOGet) {
                assertThat(dto.getName()).isEqualTo("Test Pump");
                assertThat(dto.toString()).contains("value=100");
            } else if (dto instanceof LightDTOGet) {
                assertThat(dto.getName()).isEqualTo("Test Light");
                assertThat(dto.toString()).contains("colorR=100", "colorG=120", "colorB=130");
            }
        }
    }
}
