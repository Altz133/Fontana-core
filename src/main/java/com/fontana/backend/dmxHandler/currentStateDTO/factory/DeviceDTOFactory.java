package com.fontana.backend.dmxHandler.currentStateDTO.factory;

import com.fontana.backend.devices.dto.DeviceDTO;
import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.jet.dto.JetDTO;
import com.fontana.backend.devices.led.dto.LedDTO;
import com.fontana.backend.devices.light.dto.LightDTO;
import com.fontana.backend.devices.pump.dto.PumpDTO;
import com.fontana.backend.dmxHandler.currentStateDTO.factory.messages.DeviceDTOFactoryMessages;
import org.springframework.stereotype.Service;

@Service
public class DeviceDTOFactory {

    public DeviceDTO createDeviceDTO(Device device, byte[] DMXDataArray, int[] addresses) {
        return switch (device.getType()) {
            case PUMP -> deviceToPumpDTO(device, DMXDataArray);
            case JET -> deviceToJetDTO(device, DMXDataArray);
            case LED -> deviceToLedDTO(device, DMXDataArray, addresses);
            case LIGHT -> deviceToLightDTO(device, DMXDataArray, addresses);
            default -> throw new IllegalArgumentException(DeviceDTOFactoryMessages.UNKNOWN_DEVICE.getMessage());
        };
    }

    private LedDTO deviceToLedDTO(Device device, byte[] DMXDataArray, int[] addresses) {
        return new LedDTO(device.getName(),
                DMXDataArray[addresses[0]],//R
                DMXDataArray[addresses[1]],//G
                DMXDataArray[addresses[2]],//B
                DMXDataArray[addresses[3]],//W
                DMXDataArray[addresses[4]],//POWER
                DMXDataArray[addresses[5]]);//STROBO FREQ
    }

    private LightDTO deviceToLightDTO(Device device, byte[] DMXDataArray, int[] addresses) {
        return new LightDTO(device.getName(),
                DMXDataArray[addresses[0]],//R
                DMXDataArray[addresses[1]],//G
                DMXDataArray[addresses[2]]);//B
    }

    public JetDTO deviceToJetDTO(Device device, byte[] DMXDataArray) {
        return new JetDTO(device.getName(), DMXDataArray[device.getId()] != 0);
    }

    public PumpDTO deviceToPumpDTO(Device device, byte[] DMXDataArray) {
        return new PumpDTO(device.getName(), DMXDataArray[device.getId()]);
    }
}
