package com.fontana.backend.schedule.mapper;

import com.fontana.backend.schedule.dto.ScheduleCardDto;
import com.fontana.backend.schedule.dto.ScheduleFormDto;
import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.service.player.SchedulePlayerService;
import com.fontana.backend.schedule.service.ScheduleService;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.service.TemplateServiceImpl;
import com.fontana.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduleMapper {
    private final ScheduleService scheduleService;
    private final TemplateServiceImpl templateService;
    private final UserRepository userRepository;

    public ScheduleCardDto ScheduleToScheduleCardDto(Schedule schedule) {
        return ScheduleCardDto.builder()
                .id(schedule.getId())
                .name(schedule.getName())
                .username(schedule.getUser().getUsername())
                .startTime(schedule.getStartTimestamp())
                .endTime(schedule.getEndTimestamp())
                .length(templateService.getDurationFromTemplates(schedule.getTemplates()))
                .isPlaying(SchedulePlayerService.isPlaying(schedule))
                .isCycle(scheduleService.isCycle(schedule))
                .cycleDays(List.copyOf(schedule.getCycleDays()))
                .isEnabled(schedule.isEnabled())
                .repetitions(schedule.getRepeat())
                .templateIds(schedule.getTemplates().stream().map(Template::getId).toList())
                .templateNames(schedule.getTemplates().stream().map(Template::getName).toList())
                .build();
    }

    public Schedule ScheduleFormDtoToSchedule(ScheduleFormDto scheduleFormDto) {
        return Schedule.builder()
                .id(scheduleFormDto.getId())
                .name(scheduleFormDto.getName())
                .cycleDays(Set.copyOf(scheduleFormDto.getCycleDays()))
                .startTimestamp(scheduleFormDto.getStartTime())
                .endTimestamp(scheduleFormDto.getEndTime())
                .repeat(scheduleFormDto.getRepeat())
                .user(userRepository.getReferenceById(scheduleFormDto.getUsername()))
                .templates(templateService.getTemplatesByIds(scheduleFormDto.getTemplates()))
                .enabled(scheduleFormDto.isEnabled())
                .build();
    }
}
