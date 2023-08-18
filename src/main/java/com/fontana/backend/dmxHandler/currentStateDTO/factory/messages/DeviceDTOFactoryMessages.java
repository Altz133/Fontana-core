package com.fontana.backend.dmxHandler.currentStateDTO.factory.messages;

public enum DeviceDTOFactoryMessages {
    UNKNOWN_DEVICE("Unknown device type");

    private final String message;

    DeviceDTOFactoryMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

