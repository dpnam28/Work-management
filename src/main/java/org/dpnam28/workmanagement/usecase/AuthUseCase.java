package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpnam28.workmanagement.domain.entity.Faculty;
import org.dpnam28.workmanagement.domain.entity.Major;
import org.dpnam28.workmanagement.domain.entity.PositionType;
import org.dpnam28.workmanagement.domain.entity.RoleType;
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
import org.dpnam28.workmanagement.presentation.dto.auth.AuthChangePasswordRequest;
import org.dpnam28.workmanagement.presentation.dto.auth.AuthLoginResponse;
import org.dpnam28.workmanagement.presentation.dto.auth.AuthRegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

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
        if (user.getRole() == RoleType.TEACHER) {
            user.setTeacher(teacherRepository.findById(user.getId()).orElse(null));
        } else if (user.getRole() == RoleType.STUDENT) {
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
        var role = request.getRole();
        validateCodeUniqueness(request, role);
        var major = validateStudentRequestIfNeeded(request);
        var faculty = validateTeacherRequestIfNeeded(request);
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(role)
                .build();
        User saved = userRepository.save(user);
        User managedUser = userRepository.findById(saved.getId());

        switch (role) {
            case STUDENT -> createStudentProfile(request, major, managedUser);
            case TEACHER -> createTeacherProfile(request, faculty, managedUser);
            default -> throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        attachProfile(saved, role);
        return saved;
    }

    private Major validateStudentRequestIfNeeded(AuthRegisterRequest request) {
        if (request.getRole() != RoleType.STUDENT) {
            return null;
        }
        if (request.getEntryYear() == null) {
            throw new AppException(ErrorCode.ENTRY_YEAR_IS_REQUIRED);
        }
        if (request.getMajorId() == null) {
            throw new AppException(ErrorCode.MAJOR_IS_REQUIRED);
        }
        return majorRepository.findById(request.getMajorId())
                .orElseThrow(() -> new AppException(ErrorCode.MAJOR_NOT_FOUND));
    }

    private Faculty validateTeacherRequestIfNeeded(AuthRegisterRequest request) {
        if (request.getRole() != RoleType.TEACHER) {
            return null;
        }
        if (request.getPosition() == null) {
            throw new AppException(ErrorCode.POSITION_IS_REQUIRED);
        }
        if (request.getFacultyId() == null) {
            throw new AppException(ErrorCode.FACULTY_IS_REQUIRED);
        }
        return facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
    }

    private void createStudentProfile(AuthRegisterRequest request, Major major, User managedUser) {
        Student student = new Student();
        student.setUser(managedUser);
        student.setStudentCode(request.getCode());
        student.setEntryYear(request.getEntryYear());
        student.setMajor(major);
        log.info("Creating student for user id {}", managedUser.getId());
        studentRepository.save(student);
    }

    private void createTeacherProfile(AuthRegisterRequest request, Faculty faculty, User managedUser) {
        Teacher teacher = new Teacher();
        teacher.setUser(managedUser);
        teacher.setTeacherCode(request.getCode());
        teacher.setPosition(request.getPosition());
        teacher.setFaculty(faculty);
        teacherRepository.save(teacher);
    }

    private void attachProfile(User user, RoleType role) {
        if (role == RoleType.STUDENT) {
            user.setStudent(studentRepository.findById(user.getId()).orElse(null));
        } else if (role == RoleType.TEACHER) {
            user.setTeacher(teacherRepository.findById(user.getId()).orElse(null));
        }
    }

    private void validateCodeUniqueness(AuthRegisterRequest request, RoleType role) {
        if (request.getCode() == null || request.getCode().isBlank()) {
            throw new AppException(ErrorCode.CODE_IS_REQUIRED);
        }
        boolean exists = switch (role) {
            case STUDENT -> studentRepository.existsByStudentCode(request.getCode());
            case TEACHER -> teacherRepository.existsByTeacherCode(request.getCode());
            default -> false;
        };
        if (exists) {
            throw new AppException(ErrorCode.CODE_ALREADY_EXISTS);
        }
    }

    public void changePassword(AuthChangePasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }

    public AuthLoginResponse refreshToken(String token) {
        Claims claims = extractClaimsEvenIfExpired(token);
        Long userId = claims.get("userId", Long.class);
        if (userId == null) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        attachProfile(user, user.getRole());
        PositionType position = user.getRole() == RoleType.TEACHER && user.getTeacher() != null
                ? user.getTeacher().getPosition()
                : null;
        String refreshedToken = jwtService.generateToken(user, position);
        String code = null;
        if (user.getRole() == RoleType.STUDENT && user.getStudent() != null) {
            code = user.getStudent().getStudentCode();
        } else if (user.getRole() == RoleType.TEACHER && user.getTeacher() != null) {
            code = user.getTeacher().getTeacherCode();
        }
        return AuthLoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .code(code)
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .position(position != null ? position.name() : null)
                .token(refreshedToken)
                .build();
    }

    private Claims extractClaimsEvenIfExpired(String token) {
        try {
            return jwtService.extractAllClaims(token);
        } catch (ExpiredJwtException ex) {
            return ex.getClaims();
        } catch (Exception ex) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
    }
}
