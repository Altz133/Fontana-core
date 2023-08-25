package com.fontana.backend.schedule.service.player;

public class SchedulePlayerStarter implements Runnable {
    @Override
    public void run() {
        SchedulePlayerService.start();
    }
}
