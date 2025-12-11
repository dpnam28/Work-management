package org.dpnam28.workmanagement.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpnam28.workmanagement.presentation.mapper.UserMapper;
import org.dpnam28.workmanagement.usecase.UserUseCase;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

}
