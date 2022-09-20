package com.skyvault05.remindme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skyvault05.remindme.utils.exceptions.enums.ScheduleIntervalType;
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
    private Long id;
    @Schema(example = "작성자")
    private UserDto user;
    @Schema(example = "스케쥴에 포함된 멤버들")
    private List<UserDto> members;
    @Schema(example = "스케쥴 제목", required = true)
    private String title;
    @Schema(example = "스케쥴 썸네일 이미지")
    private String thumbnail;
    @Schema(example = "스케쥴 설명")
    private String description;
    @Schema(example = "스케쥴 반복 형태 ex)ONCE, WEEKLY, MONTHLY", required = true)
    private ScheduleIntervalType intervalType;
    @Schema(example = "스케쥴 반복 기준")
    private String intervalValue;
    @Schema(example = "댓글 목록")
    private List<ScheduleReplyDto> scheduleReplies;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Schema(example = "스케쥴 시작 날짜")
    private LocalDateTime startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Schema(example = "스케쥴 종료 날짜")
    private LocalDateTime endDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime modifiedDate;
    @Schema(example = "스케쥴 상태코드")
    private Integer status;
}
