package com.fontana.backend.dmxHandler.validator.service;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.devices.entity.DeviceType;
import com.fontana.backend.devices.pump.entity.Pump;
import com.fontana.backend.frame.entity.Frame;
import com.fontana.backend.sensorsHandler.entity.Sensors;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    public void testValidateDmxData_Pump_WaterLevelBottom_False() {
        DMXValidatorService.enableApiValidation = true;
        DMXValidatorService.waterLevelTopStatus = false;
        DMXValidatorService.waterLevelBottomStatus = false;

        byte[] dmxData = new byte[256];
        Device pump = new Device(1, "Pump", new int[]{1, 2, 3}, DeviceType.PUMP);
        dmxData[1] = (byte) 100;

        DMXValidatorService.pumps = List.of(pump);

        DMXValidatorService.sensors = new Sensors();
        DMXValidatorService.sensors.setWaterBottom(false);

        byte[] result = DMXValidatorService.validatePumpsAPI(dmxData);

        assertEquals((byte) 0, result[1]);
    }

    @Test
    public void testValidateDmxData_Pump_WaterLevelBottom_True() {
        DMXValidatorService.enableApiValidation = true;
        DMXValidatorService.waterLevelTopStatus = false;
        DMXValidatorService.waterLevelBottomStatus = true;

        byte[] dmxData = new byte[256];
        Device pump = new Device(1, "Pump", new int[]{1, 2, 3}, DeviceType.PUMP);
        dmxData[1] = (byte) 100;

        DMXValidatorService.pumps = List.of(pump);

        DMXValidatorService.sensors = new Sensors();
        DMXValidatorService.sensors.setWaterBottom(true);

        byte[] result = DMXValidatorService.validatePumpsAPI(dmxData);

        assertEquals((byte) 100, result[1]);
    }

    @Test
    public void testValidateDmxData_Led_And_Light_WaterLevelTop_False(){
        DMXValidatorService.enableApiValidation = true;
        DMXValidatorService.waterLevelTopStatus = false;
        DMXValidatorService.waterLevelBottomStatus = true;

        byte[] dmxData = new byte[256];
        Device Light = new Device(1, "Light", new int[]{1, 2, 3}, DeviceType.LED);
        dmxData[1] = (byte) 100;
        dmxData[2] = (byte) 110;
        dmxData[3] = (byte) 120;

        Device led = new Device(4, "Led", new int[]{4, 5, 6,7,8,9}, DeviceType.LED);
        dmxData[4] = (byte) 130;
        dmxData[5] = (byte) 140;
        dmxData[6] = (byte) 150;
        dmxData[7] = (byte) 160;
        dmxData[8] = (byte) 170;
        dmxData[9] = (byte) 180;

        DMXValidatorService.lights = List.of(Light);
        DMXValidatorService.leds = List.of(led);

        DMXValidatorService.sensors = new Sensors();
        DMXValidatorService.sensors.setWaterTop(false);

        byte[] result = DMXValidatorService.validateLightsAndLeds(dmxData);

        assertEquals((byte) 0, result[1]);
        assertEquals((byte) 0, result[2]);
        assertEquals((byte) 0, result[3]);
        assertEquals((byte) 0, result[4]);
        assertEquals((byte) 0, result[5]);
        assertEquals((byte) 0, result[6]);
        assertEquals((byte) 0, result[7]);
        assertEquals((byte) 0, result[8]);
        assertEquals((byte) 0, result[9]);


    }

    @Test
    public void testValidateDmxData_Led_And_Light_WaterLevelTop_True(){
        DMXValidatorService.enableApiValidation = true;
        DMXValidatorService.waterLevelTopStatus = true;
        DMXValidatorService.waterLevelBottomStatus = true;

        byte[] dmxData = new byte[256];
        Device Light = new Device(1, "Light", new int[]{1, 2, 3}, DeviceType.LED);
        dmxData[1] = (byte) 100;
        dmxData[2] = (byte) 110;
        dmxData[3] = (byte) 120;

        Device led = new Device(4, "Led", new int[]{4, 5, 6,7,8,9}, DeviceType.LED);
        dmxData[4] = (byte) 130;
        dmxData[5] = (byte) 140;
        dmxData[6] = (byte) 150;
        dmxData[7] = (byte) 160;
        dmxData[8] = (byte) 170;
        dmxData[9] = (byte) 180;

        DMXValidatorService.lights = List.of(Light);
        DMXValidatorService.leds = List.of(led);

        DMXValidatorService.sensors = new Sensors();
        DMXValidatorService.sensors.setWaterTop(true);

        byte[] result = DMXValidatorService.validateLightsAndLeds(dmxData);

        assertEquals((byte) 100, result[1]);
        assertEquals((byte) 110, result[2]);
        assertEquals((byte) 120, result[3]);
        assertEquals((byte) 130, result[4]);
        assertEquals((byte) 140, result[5]);
        assertEquals((byte) 150, result[6]);
        assertEquals((byte) 160, result[7]);
        assertEquals((byte) 170, result[8]);
        assertEquals((byte) 180, result[9]);

    }

}