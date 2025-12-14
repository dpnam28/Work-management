package org.dpnam28.workmanagement.presentation.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.PlanStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponse {
    private Long id;
    private String planTitle;
    private String planDescription;
    private Long createdById;
    private Long approvedById;
    private Long facultyId;
    private PlanStatus status;
}
