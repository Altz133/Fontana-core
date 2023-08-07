package com.fontana.backend.dmxHandler.controler;

import com.fontana.backend.devices.jet.dto.JetDTO;
import com.fontana.backend.devices.jet.mapper.JetMapper;
import com.fontana.backend.devices.led.dto.LedDTO;
import com.fontana.backend.devices.led.mapper.LedMapper;
import com.fontana.backend.devices.light.dto.LightDTO;
import com.fontana.backend.devices.light.mapper.LightMapper;
import com.fontana.backend.devices.pump.dto.PumpDTO;
import com.fontana.backend.devices.pump.mapper.PumpMapper;
import com.fontana.backend.dmxHandler.service.DMXHandlerService;
import com.fontana.backend.snapshot.entity.Snapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(DMX)
@RequiredArgsConstructor
public class DMXHandlerController {

    private final DMXHandlerService DMXHandlerService;
    private final JetMapper jetMapper;
    private final PumpMapper pumpMapper;
    private final LightMapper lightMapper;
    private final LedMapper ledMapper;
    private final Snapshot snapshot;

    @PostMapping(value = DMX_UPDATE_JET)
    public ResponseEntity<Object> updateSnapshotJet(@RequestBody JetDTO jetDTO) {
        DMXHandlerService.sendDMXDataJet(jetMapper.DTOToJet(jetDTO), snapshot);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_PUMP)
    public ResponseEntity<Object> updateSnapshotPump(@RequestBody PumpDTO pumpDTO) {
        DMXHandlerService.sendDMXDataPump(pumpMapper.DTOToPump(pumpDTO), snapshot);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_LIGHT)
    public ResponseEntity<Object> updateSnapshotLight(@RequestBody LightDTO lightDTO) {
        DMXHandlerService.sendDMXDataLight(lightMapper.DTOToLight(lightDTO), snapshot);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_LED)
    public ResponseEntity<Object> updateSnapshotLed(@RequestBody LedDTO ledDTO) {
        DMXHandlerService.sendDMXDataLed(ledMapper.DTOToLed(ledDTO), snapshot);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_STATE)
    public ResponseEntity<Object> getCurrentState() {
        return ResponseEntity.ok(DMXHandlerService.getDMXDataArray());
    }

}
