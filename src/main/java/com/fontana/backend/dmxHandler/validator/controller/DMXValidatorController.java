package com.fontana.backend.dmxHandler.validator.controller;

import com.fontana.backend.dmxHandler.validator.service.DMXValidatorService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(DMX)
@RequiredArgsConstructor
public class DMXValidatorController {
    @Autowired
    private final DMXValidatorService dmxValidatorService;

    @PostMapping(value = DMX_CHANGE_PUMP_POWER_MULTIPLIER)
    public ResponseEntity<Object> changePumpMultiplier(@RequestBody @Min(value =0) @Max(value=1) float multiplier) throws IOException {
        DMXValidatorService.changePumpMultiplier(multiplier);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = DMX_UPDATE_DMX_ADDRESSES)
    public ResponseEntity<Object> updateDMXAddresses() {
        dmxValidatorService.updateDMXAddresses();
        return ResponseEntity.ok().build();
    }


}
