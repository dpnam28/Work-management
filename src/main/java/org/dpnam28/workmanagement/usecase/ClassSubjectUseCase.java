package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.ClassSubject;
import org.dpnam28.workmanagement.domain.entity.Enrollment;
import org.dpnam28.workmanagement.domain.entity.EnrollmentId;
import org.dpnam28.workmanagement.domain.entity.Student;
import org.dpnam28.workmanagement.domain.entity.Teacher;
import org.dpnam28.workmanagement.domain.entity.TeachingAssignment;
import org.dpnam28.workmanagement.domain.entity.TeachingAssignmentId;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.ClassSubjectJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.EnrollmentJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.StudentJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TeacherJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TeachingAssignmentJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassSubjectUseCase {

    private final ClassSubjectJpaRepository classSubjectRepository;
    private final EnrollmentJpaRepository enrollmentRepository;
    private final StudentJpaRepository studentRepository;
    private final TeacherJpaRepository teacherRepository;
    private final TeachingAssignmentJpaRepository teachingAssignmentRepository;

    @Transactional(readOnly = true)
    public List<ClassSubject> getBySemester(Long semesterId) {
        return classSubjectRepository.findBySemester_Id(semesterId);
    }

    @Transactional
    public void enrollStudent(Long classSubjectId, Long studentId) {
        ClassSubject classSubject = classSubjectRepository.findById(classSubjectId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_SUBJECT_NOT_FOUND));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        validateEnrollmentCapacity(classSubjectId, classSubject.getMaxStudent());
        if (enrollmentRepository.existsByStudent_IdAndClassSubject_Id(studentId, classSubjectId)) {
            throw new AppException(ErrorCode.ENROLLMENT_ALREADY_EXISTS);
        }
        Enrollment enrollment = Enrollment.builder()
                .id(new EnrollmentId(studentId, classSubjectId))
                .student(student)
                .classSubject(classSubject)
                .build();
        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void dropEnrollment(Long classSubjectId, Long studentId) {
        EnrollmentId enrollmentId = new EnrollmentId(studentId, classSubjectId);
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND));
        enrollmentRepository.delete(enrollment);
    }

    @Transactional
    public void assignTeacher(Long classSubjectId, Long teacherId) {
        ClassSubject classSubject = classSubjectRepository.findById(classSubjectId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_SUBJECT_NOT_FOUND));
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));
        if (teachingAssignmentRepository.existsByTeacher_IdAndClassSubject_Id(teacherId, classSubjectId)) {
            return;
        }
        TeachingAssignment assignment = TeachingAssignment.builder()
                .id(new TeachingAssignmentId(teacherId, classSubjectId))
                .teacher(teacher)
                .classSubject(classSubject)
                .build();
        teachingAssignmentRepository.save(assignment);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> getEnrolledStudents(Long classSubjectId) {
        ensureClassSubjectExists(classSubjectId);
        List<Enrollment> enrollments = enrollmentRepository.findByClassSubject_Id(classSubjectId);
        enrollments.forEach(enrollment -> {
            if (enrollment.getStudent() != null && enrollment.getStudent().getUser() != null) {
                enrollment.getStudent().getUser().getFullName();
            }
        });
        return enrollments;
    }

    @Transactional(readOnly = true)
    public List<TeachingAssignment> getClassSubjectTeachers(Long classSubjectId) {
        ensureClassSubjectExists(classSubjectId);
        List<TeachingAssignment> assignments = teachingAssignmentRepository.findByClassSubject_Id(classSubjectId);
        assignments.forEach(assignment -> {
            if (assignment.getTeacher() != null && assignment.getTeacher().getUser() != null) {
                assignment.getTeacher().getUser().getFullName();
            }
        });
        return assignments;
    }

    private void validateEnrollmentCapacity(Long classSubjectId, Integer maxStudent) {
        long current = enrollmentRepository.countByClassSubject_Id(classSubjectId);
        if (maxStudent != null && current >= maxStudent) {
            throw new AppException(ErrorCode.CLASS_SUBJECT_FULL);
        }
    }

    private void ensureClassSubjectExists(Long classSubjectId) {
        if (!classSubjectRepository.existsById(classSubjectId)) {
            throw new AppException(ErrorCode.CLASS_SUBJECT_NOT_FOUND);
        }
    }
}
