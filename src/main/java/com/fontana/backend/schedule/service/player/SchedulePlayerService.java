package com.fontana.backend.schedule.service.player;

import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.repository.ScheduleRepository;
import com.fontana.backend.schedule.service.ScheduleDateService;
import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.service.TemplateServiceImpl;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class SchedulePlayerService {
    public static Schedule currentSchedule = null;
    private Timestamp currentScheduleStartTimestamp = null;
    private static boolean isPlaying = false;
    private List<Integer> snapshotsSequence = new ArrayList<>();
    private Map<Integer, byte[]> snapshotData = new HashMap<>();
    private Integer currentSnapshotCounter = 0;
    private Integer currentRepetition = 0;

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> futureScheduleStartTask = scheduledExecutorService.schedule(() -> {}, 0, TimeUnit.MILLISECONDS);
    private ScheduledFuture<?> futureScheduleUpdateTask = scheduledExecutorService.schedule(() -> {}, 0, TimeUnit.MILLISECONDS);
    private Timestamp oldUpdateTimestamp = null;

    private final ScheduleDateService scheduleDateService;
    private final TemplateServiceImpl templateService;
    private final ScheduleRepository scheduleRepository;

    @PreDestroy
    private void preDestroy() {
        futureScheduleUpdateTask.cancel(true);
        futureScheduleStartTask.cancel(true);
        scheduledExecutorService.shutdownNow();
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

    private static void start() {
        if (currentSchedule != null) {
            isPlaying = true;
        }
    }

    public void updatePlayer() {
        if (oldUpdateTimestamp == null) {
            oldUpdateTimestamp = Timestamp.valueOf(LocalDateTime.now());
        }

        futureScheduleUpdateTask.cancel(true);
        futureScheduleUpdateTask = scheduledExecutorService.schedule(() -> {
            updateCurrentSchedule(oldUpdateTimestamp);
        }, 0, TimeUnit.MILLISECONDS);
    }

    public void stopAndResetCurrentSchedule() {
        futureScheduleUpdateTask.cancel(true);
        futureScheduleStartTask.cancel(true);

        reset();

        updatePlayer();
    }

    //every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    private void autoStopEndless() {
        if (currentSchedule != null && isPlaying()) {
            if (currentSchedule.getRepeat() <= 0) {
                stopAndResetCurrentSchedule();
            }
        }
    }

    private void reset() {
        currentSchedule = null;
        currentScheduleStartTimestamp = null;
        isPlaying = false;
        snapshotsSequence.clear();
        snapshotData.clear();
        currentSnapshotCounter = 0;
        currentRepetition = 0;

        oldUpdateTimestamp = null;
    }

    private void updateCurrentSchedule(Timestamp now) {
        if (!isPlaying()) {
            futureScheduleStartTask.cancel(true);
            oldUpdateTimestamp = now;

            Schedule newNextSchedule = scheduleDateService.getNextSchedule(oldUpdateTimestamp);

            if (newNextSchedule != null) {
                currentSchedule = scheduleRepository.getScheduleByIdFetchedTemplatesAndSnapshots(newNextSchedule.getId());
                currentScheduleStartTimestamp = scheduleDateService.getScheduleStartTimestamp(currentSchedule, oldUpdateTimestamp);

                loadScheduleData(currentSchedule);
                scheduleCurrentSchedule();
            }
        }
    }

    private void loadScheduleData(Schedule schedule) {
        snapshotsSequence.clear();
        snapshotData.clear();

        for (Template template : schedule.getTemplates()) {
            for (Snapshot snapshot : template.getSnapshotsSequence()) {
                for (int i = 0; i < snapshot.getDuration(); i++) {
                    snapshotsSequence.add(snapshot.getId());
                }

                snapshotData.putIfAbsent(snapshot.getId(), Arrays.copyOf(snapshot.getData(), 512));
            }
        }
    }

    private void scheduleCurrentSchedule() {
        futureScheduleStartTask.cancel(true);
        futureScheduleStartTask = scheduledExecutorService.schedule(SchedulePlayerService::start, currentScheduleStartTimestamp.getTime() - Timestamp.valueOf(LocalDateTime.now()).getTime(), TimeUnit.MILLISECONDS);
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

                    futureScheduleUpdateTask.cancel(true);
                    futureScheduleStartTask.cancel(true);
                    futureScheduleUpdateTask = scheduledExecutorService.schedule(() -> {
                        updateCurrentSchedule(Timestamp.valueOf(currentScheduleStartTimestamp.toLocalDateTime().plusSeconds(templateService.getDurationFromTemplates(currentSchedule.getTemplates()))));
                    }, 0, TimeUnit.MILLISECONDS);
                }
            }
        }

        return data;
    }

    public void panic() {
        futureScheduleUpdateTask.cancel(true);
        futureScheduleStartTask.cancel(true);

        reset();
    }
}