package org.dpnam28.workmanagement.presentation.dto.teachingassignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeachingAssignmentResponse {
    private Long teacherId;
    private Long classSubjectId;
}
