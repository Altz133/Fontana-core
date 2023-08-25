package com.fontana.backend.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulePlayer {
    private boolean isPlaying = false;

    public boolean isPlaying() {
        return isPlaying;
    }
}
