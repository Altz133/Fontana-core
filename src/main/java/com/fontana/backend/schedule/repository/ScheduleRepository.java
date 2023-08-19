package com.fontana.backend.schedule.repository;

import com.fontana.backend.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    //FIXME trzeba sprawdzic czy ten custom sql dziala
    @Query(value = "SELECT * FROM schedule s WHERE DAY(s.dates) = DAY(:date) AND MONTH(s.dates) = MONTH(:date) AND YEAR(s.dates) = YEAR(:date)", nativeQuery = true)
    List<Schedule> findByDate(@Param("date") Timestamp date);
    @Query(value = "SELECT * FROM schedule s WHERE MONTH(s.dates) = MONTH(:date) AND YEAR(s.dates) = YEAR(:date)", nativeQuery = true)
    List<Schedule> findByMonth(@Param("date") Timestamp date);
}
