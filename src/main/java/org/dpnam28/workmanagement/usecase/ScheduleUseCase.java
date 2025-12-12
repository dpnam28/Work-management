package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.ClassSubject;
import org.dpnam28.workmanagement.domain.entity.Schedule;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.ClassSubjectJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.ScheduleJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.schedule.ScheduleRequest;
import org.dpnam28.workmanagement.presentation.dto.schedule.ScheduleResponse;
import org.dpnam28.workmanagement.presentation.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleUseCase {

    private final ScheduleJpaRepository scheduleRepository;
    private final ClassSubjectJpaRepository classSubjectRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleResponse create(ScheduleRequest request) {
        if (scheduleRepository.existsByClassSubject_IdAndTime(request.getClassSubjectId(), request.getTime())) {
            throw new AppException(ErrorCode.SCHEDULE_TIME_ALREADY_EXISTS);
        }
        ClassSubject classSubject = classSubjectRepository.findById(request.getClassSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_SUBJECT_NOT_FOUND));
        Schedule schedule = new Schedule();
        schedule.setClassSubject(classSubject);
        schedule.setTime(request.getTime());
        return scheduleMapper.toResponse(scheduleRepository.save(schedule));
    }

    public ScheduleResponse update(Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));
        ClassSubject classSubject = classSubjectRepository.findById(request.getClassSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_SUBJECT_NOT_FOUND));
        if (scheduleRepository.existsByClassSubject_IdAndTimeAndIdNot(request.getClassSubjectId(),
                request.getTime(), id)) {
            throw new AppException(ErrorCode.SCHEDULE_TIME_ALREADY_EXISTS);
        }
        schedule.setClassSubject(classSubject);
        schedule.setTime(request.getTime());
        return scheduleMapper.toResponse(scheduleRepository.save(schedule));
    }

    public List<ScheduleResponse> getAll() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toResponse)
                .toList();
    }

    public ScheduleResponse getById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));
        return scheduleMapper.toResponse(schedule);
    }
}
