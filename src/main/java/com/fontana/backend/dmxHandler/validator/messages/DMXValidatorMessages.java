package com.fontana.backend.dmxHandler.validator.messages;

public enum DMXValidatorMessages {
    //I'm going to leave those here just in case we need them further down the line
    RELATIVE_POWER(" is running on too much power relative to closed valves"),
    CLOSED_VALVES(" is on but all valves are closed"),
    OVERFLOWING("Lights are on but water level is too high"),
    LOW_WATER("Water level is too low"),
    MULTIPLIER_OUT_OF_RANGE("Multiplier must be between 0 and 1"),
    DEVICE_NOT_FOUND("Device not found: "),

    INVALID_PUMP_MULTIPLIER("Pump multiplier must be between 0 and 1");

    private final String message;

    DMXValidatorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
