package com.fontana.backend.fountain.service.dmx;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class DMXService {

    Byte[] dmxData = new Byte[512+3];
    @PostConstruct
    public void init() {
        try {
            open();
            start();
        } catch (Exception e) {}
    }
    void open(){

    }
    void start(){

    }

    public void setDMXDataField(Snapshot snapshot){
        dmxData[snapshot.index]=snapshot.value;
    }

    public Byte[] getDMXDataArray() {
        //TODO
        return dmxData;
    }
}
