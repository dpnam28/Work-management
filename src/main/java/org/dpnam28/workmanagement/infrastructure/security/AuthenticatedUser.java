package org.dpnam28.workmanagement.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.dpnam28.workmanagement.domain.entity.PositionType;
import org.dpnam28.workmanagement.domain.entity.RoleType;

@Getter
@Builder
@AllArgsConstructor
public class AuthenticatedUser {
    private final Long id;
    private final String username;
    private final String email;
    private final RoleType role;
    private final PositionType position;
}
