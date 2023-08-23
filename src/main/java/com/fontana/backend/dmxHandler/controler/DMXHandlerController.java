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
import com.fontana.backend.exception.customExceptions.DMXValidatorException;
import com.fontana.backend.frame.entity.Frame;
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
    public ResponseEntity<Object> updateFrameJet(@RequestBody JetDTO jetDTO) throws IOException {
        try{
            DMXHandlerService.sendDMXDataJet(jetMapper.DTOToJet(jetDTO), frame);
            return ResponseEntity.ok().build();
        } catch (DMXValidatorException e) {
            String errorMessage = "An error occurred: " + e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @PostMapping(value = DMX_UPDATE_ARRAY)
    public ResponseEntity<Object> updateFrameArray(@RequestBody byte[] DMXDataArray) throws IOException {
        DMXHandlerService.sendDMXDataArray(DMXDataArray);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_PUMP)
    public ResponseEntity<Object> updateFramePump(@RequestBody PumpDTO pumpDTO) {
        try {
            DMXHandlerService.sendDMXDataPump(pumpMapper.DTOToPump(pumpDTO), frame);
            return ResponseEntity.ok().build();
        } catch (DMXValidatorException | IOException e) {
            String errorMessage = "An error occurred: " + e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @PostMapping(value = DMX_UPDATE_LIGHT)
    public ResponseEntity<Object> updateFrameLight(@RequestBody LightDTO lightDTO) throws IOException {
        DMXHandlerService.sendDMXDataLight(lightMapper.DTOToLight(lightDTO), frame);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_LED)
    public ResponseEntity<Object> updateFrameLed(@RequestBody LedDTO ledDTO) throws IOException {
        DMXHandlerService.sendDMXDataLed(ledMapper.DTOToLed(ledDTO), frame);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = DMX_GET_STATE)
    public ResponseEntity<Object> getCurrentState() {
        return ResponseEntity.ok(DMXHandlerService.getDMXDataArray());
    }

    @PostMapping(value = DMX_PANIC)
    public ResponseEntity<Object> panic() throws IOException {
        DMXHandlerService.panic();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_CHANGE_API_STATUS)
    public ResponseEntity<Object> changeApiStatus(@RequestBody boolean status) {
        DMXHandlerService.changeApiValidationStatus(status);
        return ResponseEntity.ok().build();
    }

}
