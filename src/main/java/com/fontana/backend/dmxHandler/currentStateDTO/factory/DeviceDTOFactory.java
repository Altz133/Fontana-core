package com.fontana.backend.dmxHandler.currentStateDTO.factory;

import com.fontana.backend.devices.dto.DeviceDTO;
import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.jet.dto.JetDTOGet;
import com.fontana.backend.devices.led.dto.LedDTOGet;
import com.fontana.backend.devices.light.dto.LightDTOGet;
import com.fontana.backend.devices.pump.dto.PumpDTOGet;
import com.fontana.backend.dmxHandler.currentStateDTO.factory.messages.DeviceDTOFactoryMessages;
import com.fontana.backend.exception.customExceptions.DeviceDTOFactoryException;
import org.springframework.stereotype.Service;

@Service
public class DeviceDTOFactory {

    public DeviceDTO createDeviceDTO(Device device, byte[] DMXDataArray, int[] addresses) {
        return switch (device.getType()) {
            case PUMP -> deviceToPumpDTOGet(device, DMXDataArray);
            case JET -> deviceToJetDTOGet(device, DMXDataArray);
            case LED -> deviceToLedDTOGet(device, DMXDataArray, addresses);
            case LIGHT -> deviceToLightDTOGet(device, DMXDataArray, addresses);
            default -> throw new DeviceDTOFactoryException(DeviceDTOFactoryMessages.UNKNOWN_DEVICE.getMessage());
        };
    }

    private LedDTOGet deviceToLedDTOGet(Device device, byte[] DMXDataArray, int[] addresses) {
        return new LedDTOGet(device.getName(),
                DMXDataArray[addresses[0]],//R
                DMXDataArray[addresses[1]],//G
                DMXDataArray[addresses[2]],//B
                DMXDataArray[addresses[3]],//W
                DMXDataArray[addresses[4]],//POWER
                DMXDataArray[addresses[5]]);//STROBO FREQ
    }

    private LightDTOGet deviceToLightDTOGet(Device device, byte[] DMXDataArray, int[] addresses) {
        return new LightDTOGet(device.getName(),
                DMXDataArray[addresses[0]],//R
                DMXDataArray[addresses[1]],//G
                DMXDataArray[addresses[2]]);//B
    }

    private JetDTOGet deviceToJetDTOGet(Device device, byte[] DMXDataArray) {
        return new JetDTOGet(device.getName(), DMXDataArray[device.getId()] != 0);
    }

    private PumpDTOGet deviceToPumpDTOGet(Device device, byte[] DMXDataArray) {
        return new PumpDTOGet(device.getName(), DMXDataArray[device.getId()]);
    }
}
