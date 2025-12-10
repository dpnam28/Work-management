package org.dpnam28.workmanagement.presentation.dto.major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MajorResponse {
    private Long id;
    private String majorName;
    private String majorCode;
    private Long facultyId;
}
