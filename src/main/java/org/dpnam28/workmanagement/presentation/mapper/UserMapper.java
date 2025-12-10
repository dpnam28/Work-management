package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.User;
import org.dpnam28.workmanagement.presentation.dto.user.UserCreationRequest;
import org.dpnam28.workmanagement.presentation.dto.user.UserResponse;
import org.dpnam28.workmanagement.presentation.dto.user.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest user);
    UserResponse toUserResponse(User user);
    void update(@MappingTarget User user, UserUpdateRequest request);
}
