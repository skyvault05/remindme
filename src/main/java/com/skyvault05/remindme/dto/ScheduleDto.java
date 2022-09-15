package com.skyvault05.remindme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skyvault05.remindme.domain.BaseTimeEntity;
import com.skyvault05.remindme.domain.ScheduleIntervalType;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScheduleDto {
    private Long scheduleId;
    private User scheduleUser;
//    private List<UserInListDto> scheduleMember;
    @Schema(example = "스케쥴 제목", required = true)
    private String scheduleTitle;
    @Schema(example = "스케쥴 썸네일 이미지")
    private String scheduleImage;
    @Schema(example = "스케쥴 설명")
    private String scheduleDescription;
    @Schema(example = "스케쥴 반복 형태 ex)ONCE, WEEKLY, MONTHLY", required = true)
    private ScheduleIntervalType scheduleIntervalType;
    @Schema(example = "스케쥴 반복 기준")
    private String scheduleIntervalValue;
    @Schema(example = "댓글 목록")
    private List<ScheduleReplyDto> scheduleReplyDto;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime scheduleEndDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime scheduleStartDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime modifiedDate;
    @Schema(example = "스케쥴 상태코드")
    private Integer scheduleStatus;
}
