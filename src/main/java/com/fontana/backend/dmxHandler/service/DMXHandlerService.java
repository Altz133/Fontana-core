package com.fontana.backend.dmxHandler.service;
import com.fontana.backend.devicesType.led.entity.Led;
import com.fontana.backend.devicesType.light.entity.Light;
import com.fontana.backend.devicesType.pump.entity.Pump;
import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.fountain.service.dmx.DMXService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import com.fontana.backend.devicesType.jet.entity.Jet;

@Service
@RequiredArgsConstructor
public class DMXHandlerService {

    private final DMXService DMXService;
    private final TaskScheduler taskScheduler;


    public void sendDMXData(Snapshot snapshot){
        DMXService.setDMXDataField(snapshot);
    }

    public void getDMXDataArray(){
        DMXService.getDMXDataArray();
    }

    public void sendDMXDataJet(Jet jet, Snapshot snapshot){
        snapshot.setId(jet.getId());
        snapshot.setValue(jet.getValue());
        DMXService.setDMXDataField(snapshot);
    }

    public void sendDMXDataPump(Pump pump, Snapshot snapshot){
        snapshot.setId(pump.getId());
        snapshot.setValue(pump.getValue());
        DMXService.setDMXDataField(snapshot);
    }

    public void sendDMXDataLight(Light light, Snapshot snapshot){

        snapshot.setId(light.getColorRID());
        snapshot.setValue(light.getColorRValue());
        DMXService.setDMXDataField(snapshot);
        snapshot.setId(light.getColorGID());
        snapshot.setValue(light.getColorGValue());
        DMXService.setDMXDataField(snapshot);
        snapshot.setId(light.getColorBID());
        snapshot.setValue(light.getColorBValue());
        DMXService.setDMXDataField(snapshot);
    }

    public void sendDMXDataLed(Led led, Snapshot snapshot){

        snapshot.setId(led.getColorRID());
        snapshot.setValue(led.getColorRValue());
        DMXService.setDMXDataField(snapshot);
        snapshot.setId(led.getColorGID());
        snapshot.setValue(led.getColorGValue());
        DMXService.setDMXDataField(snapshot);
        snapshot.setId(led.getColorBID());
        snapshot.setValue(led.getColorBValue());
        DMXService.setDMXDataField(snapshot);
        snapshot.setId(led.getColorWID());
        snapshot.setValue(led.getColorWValue());
        DMXService.setDMXDataField(snapshot);

        snapshot.setId(led.getStrobeEnabledID());
        snapshot.setValue(led.getStrobeEnabledValue());
        DMXService.setDMXDataField(snapshot);
        snapshot.setId(led.getStrobeFreqID());
        snapshot.setValue(led.getStrobeFreqValue());
        DMXService.setDMXDataField(snapshot);

    }
    }

