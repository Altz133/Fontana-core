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
    public static final String DMX_UPDATE_ARRAY = "/update/array";
    public static final String DMX_GET_STATE = "/state";
    public static final String DMX_PANIC = "/panic";
    public static final String DMX_CHANGE_API_STATUS = "/enableApiValidation";
    public static final String DMX_CHANGE_PUMP_POWER_MULTIPLIER = "/changePumpPowerMultiplier";
    public static final String DMX_UPDATE_DMX_ADDRESSES = "/updateDMXAddresses";
    public static final String DMX_GET_API_STATUS = "/getApiValidationStatus";
    public static final String DMX_GET_PUMP_POWER_MULTIPLIER = "/getPumpPowerMultiplier";

    public static final String SESSION = BASE + "/session";
    public static final String SESSION_FIND_BY_ID = "/{id}";
    public static final String SESSION_FIND_NON_DISPLAYED_AMOUNT = "/non-displayed-amount/{username}";
    public static final String SESSION_UPDATE_CLOSE = "/close";
    public static final String SESSION_UPDATE_WATCHER = "/{sessionId}/watcher";
    public static final String SESSION_UPDATE_WATCHER_ALL = "/all/watcher";

    public static final String SENSORS_URL = "http://192.168.253.29/api";

    public static final String USER = BASE + "/user";
    public static final String USER_FIND_ACTIVE = "/logged";
    public static final String USER_UPDATE_ROLE = "/{username}";

    public static final String BLACKLIST = "/blacklist";
    public static final String LOGOUT = "/logout";

    public static final String LOG = BASE + "/log";
    public static final String LOG_FIND_BY_ID = "/{id}";
    public static final String LOG_DOWNLOAD_ALL = "/download/all";

    public static final String TEMPLATE = BASE + "/template";
    public static final String TEMPLATE_DELETE = "/delete/{id}";
    public static final String TEMPLATE_MY_TEMPLATES_PAGINATION = "/myTemplatesCards";
    public static final String TEMPLATE_MY_TEMPLATES_SNIPPET = "/myTemplatesSnippet";
    public static final String TEMPLATE_DRAFT_TEMPLATES_SNIPPET = "/draftTemplatesSnippet";
    public static final String TEMPLATE_PUBLIC_TEMPLATES_PAGINATION = "/publicTemplatesCards";

    public static final String SCHEDULE = BASE + "/schedule";
    public static final String SCHEDULE_ADD = "/add";
    public static final String SCHEDULE_DELETE = "/delete/{id}";
    public static final String SCHEDULE_UPDATE = "/update";
    public static final String SCHEDULE_GET_MONTH = "/get/month";
    public static final String SCHEDULE_GET_DAY = "/get/day";
    public static final String SCHEDULE_GET_IS_PLAYING = "/get/isPlaying";
    public static final String SCHEDULE_STOP = "/stop";
    public static final String SCHEDULE_GET_SNIPPETS = "/get/snippets";
}
