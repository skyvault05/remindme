package com.skyvault05.remindme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleReplyDto {
    private Long id;
    @Schema(example = "작성자")
    private UserDto user;
    @Schema(example = "원본 스케쥴")
    private ScheduleDto schedule;
    @Schema(example = "내용", required = true)
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime modifiedDate;
    @Schema(example = "스케쥴 댓글 상태코드")
    private Integer status;

    public void deleteUnnecessaryFields(){
        this.user.deleteUnnecessaryFields();
        this.schedule.deleteUnnecessaryFields();
        this.modifiedDate = null;
    }
}
