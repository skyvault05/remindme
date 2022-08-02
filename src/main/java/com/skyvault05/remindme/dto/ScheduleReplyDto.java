package com.skyvault05.remindme.dto;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.User;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleReplyDto {
    private Long scheduleReplyId;
    private User scheduleReplyUser;
    private Schedule scheduleId;
    private String scheduleReplyDescription;
    private LocalDateTime scheduleStartDate;
    private LocalDateTime scheduleEndDate;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer scheduleReplyStatus;
}
