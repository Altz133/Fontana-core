package com.fontana.backend.schedule.service;

import com.fontana.backend.schedule.dto.ScheduleCardDTO;
import com.fontana.backend.schedule.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public void removeById(Integer id) {
        scheduleRepository.deleteById(id);
    }

    public List<ScheduleCardDTO> getScheduleByDate(){
        return scheduleRepository.findAllByCycle();
    }
}
