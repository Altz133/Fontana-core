package com.fontana.backend.dmxHandler.service;

import com.fontana.backend.fountain.service.dmx.DMXService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DMXHandlerService {

    private final DMXService DMXService;
    private final TaskScheduler taskScheduler;


    public void sendDMXData(Snapshot snapshot){
        DMXService.setDMXDataField(snapshot);
        //TODO
    }

    public void getDMXDataArray(){
        DMXService.getDMXDataArray();
        //TODO
    }

}
