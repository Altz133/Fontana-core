package com.fontana.backend.snapshot.controller;

import com.fontana.backend.snapshot.dto.SnapshotDTO;
import com.fontana.backend.snapshot.service.SnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/snapshots")
public class SnapshotController {

    @Autowired
    private SnapshotService snapshotService;

    @PostMapping("/update")
    public ResponseEntity<Void> updateSnapshot(@RequestBody SnapshotDTO snapshotDTO) {
        snapshotService.updateSnapshot(snapshotDTO.getIndex(), snapshotDTO.getValue());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}