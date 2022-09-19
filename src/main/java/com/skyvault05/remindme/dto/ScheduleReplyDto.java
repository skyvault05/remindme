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
    private Long id;
    private UserDto user;
    private Long schedule;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer status;
}
