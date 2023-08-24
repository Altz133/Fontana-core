package com.fontana.backend.dmxHandler.validator.controller;

import com.fontana.backend.dmxHandler.dto.PumpMultiplierDTO;
import com.fontana.backend.dmxHandler.validator.service.DMXValidatorService;
import com.fontana.backend.exception.customExceptions.DMXValidatorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(DMX)
@RequiredArgsConstructor
public class DMXValidatorController {
    @Autowired
    private final DMXValidatorService dmxValidatorService;

    @PutMapping(value = DMX_CHANGE_PUMP_POWER_MULTIPLIER)
    public ResponseEntity<Object> changePumpMultiplier(@RequestBody PumpMultiplierDTO pumpMultiplierDTO) {
        try {
            DMXValidatorService.changePumpMultiplier(pumpMultiplierDTO.getPumpMultiplier());
            return ResponseEntity.ok().build();
        } catch (DMXValidatorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = DMX_GET_PUMP_POWER_MULTIPLIER)
    public ResponseEntity<Object> getPumpMultiplier() {
        return ResponseEntity.ok(DMXValidatorService.getPumpMultiplier());
    }

    @PostMapping(value = DMX_UPDATE_DMX_ADDRESSES)
    public ResponseEntity<Object> updateDMXAddresses() {
        dmxValidatorService.updateDMXAddresses();
        return ResponseEntity.ok().build();
    }
}
