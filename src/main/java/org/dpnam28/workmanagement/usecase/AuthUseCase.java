package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpnam28.workmanagement.domain.entity.Student;
import org.dpnam28.workmanagement.domain.entity.Teacher;
import org.dpnam28.workmanagement.domain.entity.User;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.domain.repository.UserRepository;
import org.dpnam28.workmanagement.infrastructure.repository.FacultyJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.MajorJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.StudentJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TeacherJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.auth.AuthRegisterRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final StudentJpaRepository studentRepository;
    private final TeacherJpaRepository teacherRepository;
    private final MajorJpaRepository majorRepository;
    private final FacultyJpaRepository facultyRepository;

    public User login(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if(!encoder.matches(password, user.getPassword())){
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        if (user.getRole() == org.dpnam28.workmanagement.domain.entity.RoleType.TEACHER) {
            user.setTeacher(teacherRepository.findById(user.getId()).orElse(null));
        } else if (user.getRole() == org.dpnam28.workmanagement.domain.entity.RoleType.STUDENT) {
            user.setStudent(studentRepository.findById(user.getId()).orElse(null));
        }
        return user;
    }

    @Transactional
    public User register(AuthRegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRole())
                .build();
        User saved = userRepository.save(user);

        switch (request.getRole()) {
            case STUDENT -> {
                if (request.getEntryYear() == null) {
                    throw new AppException(ErrorCode.ENTRY_YEAR_IS_REQUIRED);
                }
                if (request.getMajorId() == null) {
                    throw new AppException(ErrorCode.MAJOR_IS_REQUIRED);
                }
                var major = majorRepository.findById(request.getMajorId())
                        .orElseThrow(() -> new AppException(ErrorCode.MAJOR_NOT_FOUND));
                var student = Student.builder()
                        .user(saved)
                        .studentCode(request.getCode())
                        .entryYear(request.getEntryYear())
                        .major(major)
                        .build();
                log.info("Creating student for user id {}", saved.getId());
                studentRepository.save(student);
            }
            case TEACHER -> {
                if (request.getPosition() == null) {
                    throw new AppException(ErrorCode.POSITION_IS_REQUIRED);
                }
                if (request.getFacultyId() == null) {
                    throw new AppException(ErrorCode.FACULTY_IS_REQUIRED);
                }
                var faculty = facultyRepository.findById(request.getFacultyId())
                        .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
                var teacher = Teacher.builder()
                        .user(saved)
                        .teacherCode(request.getCode())
                        .position(request.getPosition())
                        .faculty(faculty)
                        .build();
                teacherRepository.save(teacher);
            }
            default -> throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return saved;
    }
}
