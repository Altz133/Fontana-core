package com.fontana.backend.dmxHandler.validator.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DMXValidatorServiceTest {

    @Test
    public void testValidatePumpMultiplier_ValidMultiplier() {
        float validMultiplier = 0.5f;
        assertTrue(DMXValidatorService.validatePumpMultiplier(validMultiplier));
    }

    @Test
    public void testValidatePumpMultiplier_InvalidMultiplier_LowerBound() {
        float invalidMultiplier = -0.5f;
        assertFalse(DMXValidatorService.validatePumpMultiplier(invalidMultiplier));
    }

    @Test
    public void testValidatePumpMultiplier_InvalidMultiplier_UpperBound() {
        float invalidMultiplier = 1.5f;
        assertFalse(DMXValidatorService.validatePumpMultiplier(invalidMultiplier));
    }

}