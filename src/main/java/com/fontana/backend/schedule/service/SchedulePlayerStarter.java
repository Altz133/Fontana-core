package com.fontana.backend.schedule.service;

public class SchedulePlayerStarter implements Runnable {
    @Override
    public void run() {
        SchedulePlayerService.start();
    }
}
