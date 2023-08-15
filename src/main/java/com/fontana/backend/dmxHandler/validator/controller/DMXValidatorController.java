package com.fontana.backend.dmxHandler.validator.controller;

import com.fontana.backend.dmxHandler.validator.service.DMXValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.fontana.backend.config.RestEndpoints.DMX;
import static com.fontana.backend.config.RestEndpoints.DMX_CHANGE_PUMP_POWER_MULTIPLIER;

@RestController
@RequestMapping(DMX)
@RequiredArgsConstructor
public class DMXValidatorController {
    @Autowired
    private final DMXValidator dmxValidator;

    @PostMapping(value = DMX_CHANGE_PUMP_POWER_MULTIPLIER)
    public ResponseEntity<Object> changePumpMultiplier(@RequestBody float multiplier) throws IOException {
        DMXValidator.changePumpMultiplier(multiplier);
        return ResponseEntity.ok().build();
    }

}
