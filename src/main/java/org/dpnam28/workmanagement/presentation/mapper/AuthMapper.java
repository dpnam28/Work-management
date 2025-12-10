package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.User;
import org.dpnam28.workmanagement.presentation.dto.auth.AuthLoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthLoginResponse toAuthLoginResponse(User user);
}
