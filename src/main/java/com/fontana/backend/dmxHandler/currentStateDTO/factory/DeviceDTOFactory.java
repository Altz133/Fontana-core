package com.fontana.backend.dmxHandler.currentStateDTO.factory;

import com.fontana.backend.devices.dto.DeviceDTO;
import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.jet.dto.JetDTO;
import com.fontana.backend.devices.led.dto.LedDTO;
import com.fontana.backend.devices.light.dto.LightDTO;
import com.fontana.backend.devices.pump.dto.PumpDTO;
import org.springframework.stereotype.Service;

@Service
public class DeviceDTOFactory {
    private final String unknownDeviceMSG = "Unknown device type";

    public DeviceDTO createDeviceDTO(Device device, byte[] DMXDataArray, int[] addresses) {
        return switch (device.getType()) {
            case PUMP -> deviceToJetDTO(device, DMXDataArray);
            case JET -> deviceToPumpDTO(device, DMXDataArray);
            case LED -> deviceToLedDTO(device, DMXDataArray, addresses);
            case LIGHT -> deviceToLightDTO(device, DMXDataArray, addresses);
            default -> throw new IllegalArgumentException(unknownDeviceMSG);
        };
    }

    private LedDTO deviceToLightDTO(Device device, byte[] DMXDataArray, int[] addresses) {
        return new LedDTO(DMXDataArray[addresses[0]],
                DMXDataArray[addresses[1]],
                DMXDataArray[addresses[2]],
                DMXDataArray[addresses[3]],
                DMXDataArray[addresses[4]],
                DMXDataArray[addresses[5]]);
    }

    private LightDTO deviceToLedDTO(Device device, byte[] DMXDataArray, int[] addresses) {
        return new LightDTO(
                DMXDataArray[addresses[0]],
                DMXDataArray[addresses[1]],
                DMXDataArray[addresses[2]]);
    }

    public JetDTO deviceToJetDTO(Device device, byte[] DMXDataArray) {
        //moge zrobic mappera na jeta i z jeta na jetdto ale to jest szybsze
        return new JetDTO(device.getName(), DMXDataArray[device.getId()] != 0);
    }

    public PumpDTO deviceToPumpDTO(Device device, byte[] DMXDataArray) {
        return new PumpDTO(device.getName(),DMXDataArray[device.getId()]);}
}
