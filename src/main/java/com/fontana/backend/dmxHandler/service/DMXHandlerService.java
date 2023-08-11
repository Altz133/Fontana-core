package com.fontana.backend.dmxHandler.service;

import com.fontana.backend.devices.jet.entity.Jet;
import com.fontana.backend.devices.led.entity.Led;
import com.fontana.backend.devices.light.entity.Light;
import com.fontana.backend.devices.pump.entity.Pump;
import com.fontana.backend.dmxHandler.DMXService;
import com.fontana.backend.frame.entity.Frame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DMXHandlerService {

    private final DMXService DMXService;

    public void sendDMXData(Frame frame) throws IOException {
        DMXService.setDMXDataField(frame);
    }

    public byte[] getDMXDataArray() {
        return DMXService.getDMXDataArray();
    }

    public void sendDMXDataJet(Jet jet, Frame frame) throws IOException {
        frame.setId(jet.getId());
        frame.setValue(jet.getValue());
        DMXService.setDMXDataField(frame);
    }

    public void sendDMXDataPump(Pump pump, Frame frame) throws IOException {
        frame.setId(pump.getId());
        frame.setValue(pump.getValue());
        DMXService.setDMXDataField(frame);
    }

    public void sendDMXDataLight(Light light, Frame frame) throws IOException {
        frame.setId(light.getColorRID());
        frame.setValue(light.getColorRValue());
        DMXService.setDMXDataField(frame);
        frame.setId(light.getColorGID());
        frame.setValue(light.getColorGValue());
        DMXService.setDMXDataField(frame);
        frame.setId(light.getColorBID());
        frame.setValue(light.getColorBValue());
        DMXService.setDMXDataField(frame);
    }

    public void sendDMXDataLed(Led led, Frame frame) throws IOException {
        frame.setId(led.getColorRID());
        frame.setValue(led.getColorRValue());
        DMXService.setDMXDataField(frame);
        frame.setId(led.getColorGID());
        frame.setValue(led.getColorGValue());
        DMXService.setDMXDataField(frame);
        frame.setId(led.getColorBID());
        frame.setValue(led.getColorBValue());
        DMXService.setDMXDataField(frame);
        frame.setId(led.getColorWID());
        frame.setValue(led.getColorWValue());
        DMXService.setDMXDataField(frame);

        frame.setId(led.getDimmID());
        frame.setValue(led.getDimmValue());
        DMXService.setDMXDataField(frame);
        frame.setId(led.getStrobeFreqID());
        frame.setValue(led.getStrobeFreqValue());
        DMXService.setDMXDataField(frame);

    }

    public void closeConnection() throws IOException {
        DMXService.closeConnection();
    }

    public void openConnection() throws IOException {
        DMXService.openConnection();
    }
}

