package com.fontana.backend.snapshot.controller;

import com.fontana.backend.snapshot.mapper.SnapshotMapper;
import com.fontana.backend.snapshot.service.SnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static com.fontana.backend.config.RestEndpoints.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(SNAPSHOT)
public class SnapshotController {
    SnapshotService snapshotService;
    SnapshotMapper snapshotMapper;

//    @PostMapping
//    public void postSnapshot(@RequestBody SnapshotRequestDto snapshotRequestDto){
//        this.snapshotService.addSnapshot(this.snapshotMapper.map(snapshotRequestDto));
//    }

}
