package com.fontana.backend.schedule.repository;

import com.fontana.backend.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(value = "SELECT s FROM Schedule s WHERE (s.endTimestamp is NULL or s.endTimestamp >= ?1) AND s.startTimestamp < ?2")
    List<Schedule> getSchedulesInTimestampRange(Long start, Long end);

    @Query(value = "SELECT s FROM Schedule s WHERE (s.endTimestamp is NULL or s.endTimestamp >= ?1) AND s.enabled = true")
    List<Schedule> getEnabledSchedulesInFutureFrom(Long start);

    @Query(value = "SELECT s FROM Schedule s JOIN FETCH s.templates as t join fetch t.snapshotsSequence WHERE s.id = ?1")
    Schedule getScheduleByIdFetchedTemplatesAndSnapshots(Integer id);
}
