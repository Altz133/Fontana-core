package com.fontana.backend.dmxHandler.service;

import com.fontana.backend.fountain.service.dmx.DMXService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DMXHandlerService {
    private Byte[] dmxData = new Byte[512+3];/*3 na zakonczenie tablicy ZAWSZE!!*/

    private final DMXService theDMXService;

    public void translateJSON(String JSON) {
        System.out.println("JSON: "+JSON);
        for (int i = 0; i < 512; i++) {

            dmxData[i] = 0;
        }
    }

    public void fillDMXData(){
        for(int i=0;i<512;i++){
            dmxData[i]=0;
        }
    }

    public void sendDMXData(String JSON){
        fillDMXData();
        translateJSON(JSON);
        theDMXService.setDMXData(dmxData);
        //TODO
    }






}
