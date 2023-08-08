package com.fontana.backend.config;

public class RestEndpoints {

    public static final String BASE = "/fontana/api/v1";

    public static final String AUTH = BASE + "/auth";
    public static final String AUTH_AUTHENTICATE = "/authenticate";
    public static final String AUTH_REFRESHTOKEN = "/refreshtoken";

    public static final String DMX = BASE + "/dmx";
    public static final String DMX_UPDATE_JET = "/update/jet";
    public static final String DMX_UPDATE_PUMP = "/update/pump";
    public static final String DMX_UPDATE_LIGHT = "/update/light";
    public static final String DMX_UPDATE_LED = "/update/led";
    public static final String DMX_GET_STATE = "/state";
    public static final String DMX_CLOSE_CONNECTION = "/close";
    public static final String DMX_OPEN_CONNECTION = "/open";
}