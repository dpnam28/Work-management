package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.Faculty;
import org.dpnam28.workmanagement.domain.entity.Meeting;
import org.dpnam28.workmanagement.domain.entity.PositionType;
import org.dpnam28.workmanagement.domain.entity.Teacher;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.FacultyJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.MeetingJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TeacherJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.meeting.MeetingRequest;
import org.dpnam28.workmanagement.presentation.dto.meeting.MeetingResponse;
import org.dpnam28.workmanagement.presentation.dto.meeting.MeetingUpdateRequest;
import org.dpnam28.workmanagement.presentation.mapper.MeetingMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingUseCase {

    private final MeetingJpaRepository meetingRepository;
    private final TeacherJpaRepository teacherRepository;
    private final FacultyJpaRepository facultyRepository;
    private final MeetingMapper meetingMapper;

    public MeetingResponse create(Long requesterId, MeetingRequest request) {
        Teacher headTeacher = requireHeadTeacher(requesterId);
        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
        Meeting meeting = new Meeting();
        meeting.setCreatedBy(headTeacher);
        meeting.setFaculty(faculty);
        meeting.setMeetingTitle(request.getMeetingTitle());
        meeting.setMeetingDescription(request.getMeetingDescription());
        meeting.setDate(request.getDate());
        return meetingMapper.toResponse(meetingRepository.save(meeting));
    }

    public MeetingResponse update(Long meetingId, Long requesterId, MeetingUpdateRequest request) {
        requireHeadTeacher(requesterId);
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new AppException(ErrorCode.MEETING_NOT_FOUND));
        meeting.setMeetingTitle(request.getMeetingTitle());
        meeting.setMeetingDescription(request.getMeetingDescription());
        meeting.setDate(request.getDate());
        return meetingMapper.toResponse(meetingRepository.save(meeting));
    }

    public void delete(Long meetingId, Long requesterId) {
        requireHeadTeacher(requesterId);
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new AppException(ErrorCode.MEETING_NOT_FOUND));
        meetingRepository.delete(meeting);
    }

    private Teacher requireHeadTeacher(Long teacherId) {
        if (teacherId == null) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));
        if (teacher.getPosition() != PositionType.HEAD) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return teacher;
    }
}
