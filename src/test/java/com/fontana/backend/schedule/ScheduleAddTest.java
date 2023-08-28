package com.fontana.backend.schedule;

import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ScheduleAddTest {
    @Mock
    Schedule schedule1;

    @MockBean
    ScheduleRepository scheduleRepository;

    @BeforeEach
    private void before() {
        schedule1 = Schedule.builder()
                .name("schedule 1")
                .id(1)
                .user(null)
                .enabled(true)
                .templates(new ArrayList<>())
                .repeat(0)
                .startTimestamp(Timestamp.valueOf(LocalDateTime.now()))
                .endTimestamp(null)
                .cycleDays(new HashSet<>())
                .build();
    }

    @Test
    public void addScheduleWithNullUser_Failure() {
        when(scheduleRepository.save(schedule1)).thenThrow();

        try {
            scheduleRepository.save(schedule1);
        } catch (Exception e) {

        }

        assertFalse(scheduleRepository.existsById(schedule1.getId()));

        verify(scheduleRepository, times(1)).save(schedule1);
    }
}
