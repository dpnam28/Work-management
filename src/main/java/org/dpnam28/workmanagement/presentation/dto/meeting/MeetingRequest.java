package org.dpnam28.workmanagement.presentation.dto.meeting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRequest {
    @NotNull(message = "Created by is required")
    private Long createdById;

    @NotNull(message = "Faculty is required")
    private Long facultyId;

    @NotBlank(message = "Meeting title is required")
    private String meetingTitle;

    private String meetingDescription;

    @NotNull(message = "Date is required")
    private LocalDate date;
}
