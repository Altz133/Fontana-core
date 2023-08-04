package com.fontana.backend.dmxHandler.controler;

import com.fontana.backend.dmxHandler.service.DMXHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fontana/api/v1/JSON")
@RequiredArgsConstructor
public class DMXHandlerController {

    private final DMXHandlerService DMXHandlerService;

    @PostMapping(value ="/update")
    public ResponseEntity<Object> updateSnapshot(@RequestBody Snapshot snapshotDTO){
        /*translator na snapshot*/
        /*
        SnapshotDTO - name, value
        Snapshot - index, value
        */
        /*na razie po prostu snapshot*/
        DMXHandlerService.sendDMXData(Snapshot snapshot);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value ="/getCurrentState")
    public ResponseEntity<Object> getCurrentState(){
        /*TODO To bedzie to returnowalo do body*/
        DMXHandlerService.getDMXDataArray();
        return ResponseEntity.ok().build();
    }

}
