package com.fontana.backend.schedule.service;

import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.template.entity.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class SchedulePlayerService {
    private static boolean isPlaying = false;
    private List<Integer> snapshotsSequence = new ArrayList<>();
    private Map<Integer, byte[]> snapshotData = new HashMap<>();
    private Integer currentSnapshotCounter = 0;
    private Integer currentRepetition = 0;
    private static Schedule currentSchedule = null;

    private final ScheduleDateService scheduleDateService;
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void pauseCurrentSchedule() {
        isPlaying = false;
    }

    public void continueCurrentSchedule() {
        isPlaying = true;
    }

    public static void start() {
        isPlaying = true;
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

    public static boolean isPlaying(Schedule schedule) {
        if (currentSchedule == null) {
            return false;
        } else {
            return Objects.equals(schedule.getId(), currentSchedule.getId()) && isPlaying;
        }
    }

    public void stop() {
        reset();
        updateCurrentSchedule();
    }

    //every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    private void autoStop() {
        if (currentSchedule != null) {
            if (currentSchedule.getRepeat() == 0) {
                reset();
                updateCurrentSchedule();
            }
        }
    }

    private void reset() {
        isPlaying = false;
        snapshotsSequence.clear();
        snapshotData.clear();
        currentSnapshotCounter = 0;
        currentRepetition = 0;
        currentSchedule = null;
    }

    public void updateCurrentSchedule() {
        currentSchedule = scheduleDateService.getNextSchedule();

        if (currentSchedule != null) {
            loadScheduleData(currentSchedule);
            scheduleSchedule(currentSchedule);
        }
    }

    private void loadScheduleData(Schedule schedule) {
        for (Template template : schedule.getTemplates()) {
            snapshotsSequence.addAll(template.getSnapshotsSequence().stream().map(Snapshot::getId).toList());

            for (Snapshot snapshot : template.getSnapshotsSequence()) {
                snapshotData.putIfAbsent(snapshot.getId(), Arrays.copyOf(snapshot.getData(), 512));
            }
        }
    }

    private void scheduleSchedule(Schedule schedule) {
        scheduledExecutorService.shutdownNow();

        long timeLeft = scheduleDateService.getScheduleStartTimestamp(currentSchedule).getTime() - Timestamp.valueOf(LocalDateTime.now()).getTime();
        scheduledExecutorService.schedule(new SchedulePlayerStarter(), timeLeft, TimeUnit.MILLISECONDS);
    }

    public byte[] nextDmxData() {
        byte[] data = Arrays.copyOf(snapshotData.get(snapshotsSequence.get(currentSnapshotCounter)), 515);
        data[512] = 33;
        data[513] = 22;
        data[514] = 11;

        currentSnapshotCounter += 1;
        if (currentSchedule.getRepeat() <= 0) {
            currentSnapshotCounter = currentSnapshotCounter % snapshotsSequence.size();
        } else {
            if (currentSnapshotCounter % snapshotsSequence.size() == 0) {
                currentSnapshotCounter = 0;
                currentRepetition += 1;

                if (currentRepetition >= currentSchedule.getRepeat()) {
                    isPlaying = false;
                }
            }
        }

        return data;
    }
}
