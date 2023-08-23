package com.fontana.backend.schedule.service;

import org.springframework.stereotype.Service;

@Service
public class SchedulePlayer {
    private boolean isPlaying = false;

    public boolean isPlaying() {
        return isPlaying;
    }
}
