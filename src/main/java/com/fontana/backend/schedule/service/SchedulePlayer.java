package com.fontana.backend.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulePlayer {
    private static boolean isPlaying = false;

    public static boolean isPlaying() {
        return isPlaying;
    }
}
