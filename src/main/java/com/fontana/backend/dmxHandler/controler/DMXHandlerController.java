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
import com.fontana.backend.frame.entity.Frame;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    private final Frame frame;

    @PostMapping(value = DMX_UPDATE_JET)
    public ResponseEntity<Object> updateFrameJet(@Valid @RequestBody JetDTO jetDTO) {
        DMXHandlerService.sendDMXDataJet(jetMapper.DTOToJet(jetDTO), frame);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_PUMP)
    public ResponseEntity<Object> updateFramePump(@Valid @RequestBody PumpDTO pumpDTO) {
        DMXHandlerService.sendDMXDataPump(pumpMapper.DTOToPump(pumpDTO), frame);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_LIGHT)
    public ResponseEntity<Object> updateFrameLight(@Valid @RequestBody LightDTO lightDTO) {
        DMXHandlerService.sendDMXDataLight(lightMapper.DTOToLight(lightDTO), frame);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_LED)
    public ResponseEntity<Object> updateFrameLed(@Valid @RequestBody LedDTO ledDTO) {
        DMXHandlerService.sendDMXDataLed(ledMapper.DTOToLed(ledDTO), frame);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = DMX_GET_STATE)
    public ResponseEntity<Object> getCurrentState() {
        return ResponseEntity.ok(DMXHandlerService.getDMXDataArray());
    }

    @GetMapping(value = DMX_CLOSE_CONNECTION)
    public ResponseEntity<Object> closeConnection() throws IOException {
        DMXHandlerService.closeConnection();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_OPEN_CONNECTION)
    public ResponseEntity<Object> openConnection() throws IOException {
        DMXHandlerService.openConnection();
        return ResponseEntity.ok().build();
    }

}
