package com.fontana.backend.dmxHandler.controler;

import com.fontana.backend.dmxHandler.service.DMXHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fontana/api/v1/JSON")
@RequiredArgsConstructor
public class DMXHandlerController {

    private final DMXHandlerService theDMXJSONInterpreter;

    @GetMapping(value ="/interpret")
    public ResponseEntity<Object> interpretSnapshot(){
        /*interpreer oddzielic i a tak to git*/
        return ResponseEntity.ok().build();
    }

}
