package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.domain.entity.User;
import org.dpnam28.workmanagement.domain.entity.PositionType;
import org.dpnam28.workmanagement.domain.entity.RoleType;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.presentation.dto.auth.AuthInspectResponse;
import org.dpnam28.workmanagement.presentation.dto.auth.AuthLoginRequest;
import org.dpnam28.workmanagement.presentation.dto.auth.AuthLoginResponse;
import org.dpnam28.workmanagement.presentation.dto.auth.AuthRegisterRequest;
import org.dpnam28.workmanagement.presentation.mapper.AuthMapper;
import org.dpnam28.workmanagement.usecase.AuthUseCase;
import org.dpnam28.workmanagement.usecase.JwtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;
    private final AuthMapper authMapper;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ApiResponse<AuthLoginResponse> login(@RequestBody @Valid AuthLoginRequest user){
        User userLogin = authUseCase.login(user.getEmail(), user.getPassword());
        PositionType position = userLogin.getTeacher() != null ? userLogin.getTeacher().getPosition() : null;
        String token = jwtService.generateToken(userLogin, position);
        AuthLoginResponse response = AuthLoginResponse.builder()
                .id(userLogin.getId())
                .username(userLogin.getUsername())
                .email(userLogin.getEmail())
                .code(userLogin.getStudent() != null ? userLogin.getStudent().getStudentCode() : userLogin.getTeacher().getTeacherCode())
                .fullName(userLogin.getFullName())
                .role(userLogin.getRole().name())
                .position(position != null ? position.name() : null)
                .token(token)
                .build();
        return ApiResponse.apiResponseSuccess("Login succeeded", response);
    }

    @PostMapping("/register")
    public ApiResponse<AuthLoginResponse> register(@RequestBody @Valid AuthRegisterRequest request) {
        User created = authUseCase.register(request);
        PositionType position = created.getRole() == RoleType.TEACHER ? request.getPosition() : null;
        AuthLoginResponse response = AuthLoginResponse.builder()
                .id(created.getId())
                .username(created.getUsername())
                .email(created.getEmail())
                .fullName(created.getFullName())
                .role(created.getRole().name())
                .position(position != null ? position.name() : null)
                .build();
        return ApiResponse.apiResponseSuccess("Register succeeded", response);
    }

    @GetMapping("/inspector")
    public ApiResponse<AuthInspectResponse> inspect(@RequestParam("token") String token) {
        try {
            var claims = jwtService.extractAllClaims(token);
            AuthInspectResponse response = AuthInspectResponse.builder()
                    .userId(claims.get("userId", Long.class))
                    .username(jwtService.extractUsername(token))
                    .email(claims.get("email", String.class))
                    .role(claims.get("role", String.class))
                    .position(claims.get("position", String.class))
                    .valid(true)
                    .build();
            return ApiResponse.apiResponseSuccess("Token is valid", response);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
    }
}
