package com.fontana.backend.dmxHandler.controler;

import com.fontana.backend.devicesType.jet.dto.JetDTO;
import com.fontana.backend.devicesType.led.dto.LedDTO;
import com.fontana.backend.devicesType.light.dto.LightDTO;
import com.fontana.backend.dmxHandler.service.DMXHandlerService;
import com.fontana.backend.snapshot.entity.Snapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fontana.backend.devicesType.pump.dto.PumpDTO;

/*FIXME to nie powinno sie injectowac?*/
import com.fontana.backend.devicesType.jet.mapper.JetMapper;
import com.fontana.backend.devicesType.pump.mapper.PumpMapper;
import com.fontana.backend.devicesType.light.mapper.LightMapper;
import com.fontana.backend.devicesType.led.mapper.LedMapper;
@RestController
@RequestMapping("/fontana/api/v1/dmx")
@RequiredArgsConstructor
public class DMXHandlerController {

    private final DMXHandlerService DMXHandlerService;
    private final JetMapper JetMapper;
    private final PumpMapper PumpMapper;
    private final LightMapper LightMapper;
    private final LedMapper LedMapper;
    private final Snapshot Snapshot;

    /*FIXME w poniedzialek sie zobaczy*/

    @PostMapping(value ="/update/jet")
    public ResponseEntity<Object> updateSnapshotJet(@RequestBody JetDTO jetDTO){
        DMXHandlerService.sendDMXDataJet(JetMapper.DTOToJet(jetDTO), Snapshot);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value ="/update/pump")
    public ResponseEntity<Object> updateSnapshotPump(@RequestBody PumpDTO pumpDTO){
        DMXHandlerService.sendDMXDataPump(PumpMapper.DTOToPump(pumpDTO), Snapshot);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value ="/update/light")
    public ResponseEntity<Object> updateSnapshotLight(@RequestBody LightDTO lightDTO){
        DMXHandlerService.sendDMXDataLight(LightMapper.DTOToLight(lightDTO), Snapshot);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value ="/update/led")
    public ResponseEntity<Object> updateSnapshotLed(@RequestBody LedDTO ledDTO){
        DMXHandlerService.sendDMXDataLed(LedMapper.DTOToLed(ledDTO), Snapshot);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value ="/getCurrentState")
    public ResponseEntity<Object> getCurrentState(){
        return ResponseEntity.ok(DMXHandlerService.getDMXDataArray());
    }

}
