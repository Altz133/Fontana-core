package com.fontana.backend.sensorsHandler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fontana.backend.sensorsHandler.entity.Sensors;
import lombok.NoArgsConstructor;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.fontana.backend.config.RestEndpoints.SENSORS_URL;

@NoArgsConstructor
@Service
public class SensorsHandlerService {
    Response response;
    @Autowired
    ObjectMapper objectMapper;

    public Sensors getSensors() throws IOException {

        response = Request.get(SENSORS_URL).execute();
        return objectMapper.readValue(response.returnContent().asString(), Sensors.class);

    }




}
