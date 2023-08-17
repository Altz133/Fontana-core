package com.fontana.backend.dmxHandler.validator.messages;

public enum DMXValidatorMessages {
    RELATIVE_POWER(" is running on too much power relative to closed valves"),
    CLOSED_VALVES(" is on but all valves are closed"),
    OVERFLOWING("Lights are on but water level is too high"),
    LOW_WATER("Water level is too low");

    private final String message;

    DMXValidatorMessages(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }



}
