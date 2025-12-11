package org.dpnam28.workmanagement.presentation.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthInspectResponse {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String position;
    private boolean valid;
}
