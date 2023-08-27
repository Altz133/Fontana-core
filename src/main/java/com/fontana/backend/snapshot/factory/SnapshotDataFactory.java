package com.fontana.backend.snapshot.factory;

import com.fontana.backend.devices.dto.DeviceDTO;
import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.jet.dto.JetDTO;
import com.fontana.backend.devices.led.dto.LedDTO;
import com.fontana.backend.devices.light.dto.LightDTO;
import com.fontana.backend.devices.pump.dto.PumpDTO;
import com.fontana.backend.devices.pump.entity.Pump;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.dmxHandler.currentStateDTO.factory.messages.DeviceDTOFactoryMessages;
import com.fontana.backend.exception.customExceptions.DeviceDTOFactoryException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SnapshotDataFactory {

    @Autowired
    private DeviceRepository deviceRepository;

    public byte[] createData(DeviceDTO deviceDto, byte[] snapshotData) {
        Device device = deviceRepository.findByName(deviceDto.getName());

        return switch (device.getType()) {
            case PUMP -> setPumpValue(device, deviceDto, snapshotData);
            case JET -> setJetValue(device,deviceDto, snapshotData);
            case LED -> setLedValue(device,deviceDto, snapshotData);
            case LIGHT -> setLightValue(device,deviceDto, snapshotData);
            default -> throw new DeviceDTOFactoryException(DeviceDTOFactoryMessages.UNKNOWN_DEVICE.getMessage());
        };
    }

    public byte[] setPumpValue(Device device,DeviceDTO deviceDto, byte[] snapshotData){
        PumpDTO pumpDTO = (PumpDTO) deviceDto;
        snapshotData[device.getId()] = pumpDTO.getValue();
        return snapshotData;
    }
    private byte[] setJetValue(Device device,DeviceDTO deviceDto, byte[] snapshotData) {
        JetDTO jetDTO = (JetDTO) deviceDto;
        snapshotData[device.getId()] = (byte) (jetDTO.getValue() ? 1 : 0);
        return snapshotData;
    }
    private byte[] setLedValue(Device device,DeviceDTO deviceDto, byte[] snapshotData) {
        LedDTO ledDTO = (LedDTO) deviceDto;
        snapshotData[device.getAddress()[0]] = ledDTO.getColorR();
        snapshotData[device.getAddress()[1]] = ledDTO.getColorG();
        snapshotData[device.getAddress()[2]] = ledDTO.getColorB();
        snapshotData[device.getAddress()[3]] = ledDTO.getColorW();
        snapshotData[device.getAddress()[4]] = ledDTO.getPower();
        snapshotData[device.getAddress()[5]] = ledDTO.getStroboscopeFrequency();

        return snapshotData;
    }
    private byte[] setLightValue(Device device,DeviceDTO deviceDto, byte[] snapshotData) {
        LightDTO lightDTO = (LightDTO) deviceDto;
        snapshotData[device.getAddress()[0]] = lightDTO.getColorR();
        snapshotData[device.getAddress()[1]] = lightDTO.getColorG();
        snapshotData[device.getAddress()[2]] = lightDTO.getColorB();

        return snapshotData;
    }


}
