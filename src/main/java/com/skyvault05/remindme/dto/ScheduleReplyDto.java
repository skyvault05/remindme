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
    private UserDto scheduleReplyUser;
    private Long scheduleId;
    private String scheduleReplyDescription;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer scheduleReplyStatus;
}
