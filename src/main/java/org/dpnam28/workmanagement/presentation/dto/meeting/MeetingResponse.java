package org.dpnam28.workmanagement.presentation.dto.meeting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingResponse {
    private Long id;
    private Long createdById;
    private Long facultyId;
    private String meetingTitle;
    private String meetingDescription;
    private LocalDateTime date;
}
