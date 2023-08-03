package com.fontana.backend.dmxJSONInterpreter.controler;

import com.fontana.backend.dmxJSONInterpreter.service.DMXJSONInterpreter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fontana/api/v1/JSON")
@RequiredArgsConstructor
public class DMXJSONInterpreterController {

    private final DMXJSONInterpreter theDMXJSONInterpreter;

    @GetMapping(value ="/interpret")
    public ResponseEntity<Object> interpretJSON(){
        /*interpreer oddzielic i a tak to git*/
        return ResponseEntity.ok().build();
    }

}
