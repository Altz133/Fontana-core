package com.fontana.backend.snapshot.factory;

import com.fontana.backend.devices.jet.dto.JetDTO;
import com.fontana.backend.devices.jet.entity.Jet;
import com.fontana.backend.devices.jet.mapper.JetMapper;
import com.fontana.backend.devices.led.dto.LedDTO;
import com.fontana.backend.devices.led.entity.Led;
import com.fontana.backend.devices.led.mapper.LedMapper;
import com.fontana.backend.devices.light.dto.LightDTO;
import com.fontana.backend.devices.light.entity.Light;
import com.fontana.backend.devices.light.mapper.LightMapper;
import com.fontana.backend.devices.pump.dto.PumpDTO;
import com.fontana.backend.devices.pump.entity.Pump;
import com.fontana.backend.devices.pump.mapper.PumpMapper;
import com.fontana.backend.snapshot.dto.DevicesRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SnapshotDataFactory {

    @Autowired
    private JetMapper jetMapper;
    private PumpMapper pumpMapper;
    private LedMapper ledMapper;
    private LightMapper lightMapper;


    public byte[] createData(DevicesRequestDto deviceDto, byte[] snapshotData) {
        for (int i = 0; i < deviceDto.getJets().size(); i++) {
            snapshotData = setJetValue(deviceDto.getJets().get(i), snapshotData);
        }
        for (int i = 0; i < deviceDto.getPumps().size(); i++) {
            snapshotData = setPumpValue(deviceDto.getPumps().get(i), snapshotData);
        }
        for (int i = 0; i < deviceDto.getLights().size(); i++) {
            snapshotData = setLightValue(deviceDto.getLights().get(i), snapshotData);
        }
        for (int i =0;i<deviceDto.getFoams().size(); i++){
            snapshotData = setJetValue(deviceDto.getFoams().get(i),snapshotData );
        }

        snapshotData = setLedValue(deviceDto.getLeds().get(0), snapshotData);

        return snapshotData;
    }

    private byte[] setJetValue(JetDTO jetDto, byte[] snapshotData) {
        Jet jet = jetMapper.DTOToJet(jetDto);

        snapshotData[jet.getId()] = jet.getValue();
        return snapshotData;
    }

    public byte[] setPumpValue(PumpDTO pumpDTO, byte[] snapshotData) {
        Pump pump = pumpMapper.DTOToPump(pumpDTO);
        snapshotData[pump.getId()] = pump.getValue();
        return snapshotData;
    }

    private byte[] setLedValue(LedDTO ledDTO, byte[] snapshotData) {
        Led led = ledMapper.DTOToLed(ledDTO);
        snapshotData[led.getColorRID()] = led.getColorRValue();
        snapshotData[led.getColorGID()] = led.getColorGValue();
        snapshotData[led.getColorBID()] = led.getColorBValue();
        snapshotData[led.getColorWID()] = led.getColorWValue();
        snapshotData[led.getDimmID()] = led.getDimmValue();
        snapshotData[led.getStrobeFreqID()] = led.getStrobeFreqValue();
        return snapshotData;
    }

    private byte[] setLightValue(LightDTO lightDTO, byte[] snapshotData) {
        Light light = lightMapper.DTOToLight(lightDTO);
        snapshotData[light.getColorRID()] = light.getColorRValue();
        snapshotData[light.getColorGID()] = light.getColorGValue();
        snapshotData[light.getColorBID()] = light.getColorBValue();
        return snapshotData;
    }
}
