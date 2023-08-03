package com.fontana.backend.snapshots.controller;

import com.fontana.backend.snapshots.dto.SnapshotsDTO;
import com.fontana.backend.snapshots.service.SnapshotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/snapshots")
public class SnapshotsController {

    @Autowired
    private SnapshotsService snapshotService;

    @PostMapping("/update")
    public ResponseEntity<Void> updateSnapshot(@RequestBody SnapshotsDTO snapshotDTO) {
        snapshotService.updateSnapshot(snapshotDTO.getIndex(), snapshotDTO.getValue());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

