package com.skyvault05.remindme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skyvault05.remindme.utils.enums.ScheduleIntervalType;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
//    @Schema(example = "작성자")
    private SimpleUserDto user;
//    @Schema(example = "스케쥴에 포함된 멤버들")
    private List<SimpleUserDto> members;
//    @Schema(example = "스케쥴 제목", required = true)
    private String title;
//    @Schema(example = "스케쥴 썸네일 이미지 주소")
    private String thumbnail;
//    @Schema(example = "스케쥴 썸네일 이미지 파일. 파일 업로드시 사용.")
    private MultipartFile ThumbnailImage;
//    @Schema(example = "스케쥴 설명")
    private String description;
//    @Schema(example = "스케쥴 반복 형태 ex) ONCE, WEEKLY, MONTHLY", required = true)
    private ScheduleIntervalType intervalType;
//    @Schema(example = "스케쥴 반복 횟수")
    private Integer intervalValue;
//    @Schema(example = "스케쥴 소요시간")
    private String duration;
    private List<ScheduleReplyDto> scheduleReplies;
//    @Schema(example = "스케쥴 시작 날짜")
    private LocalDateTime startDate;
//    @Schema(example = "스케쥴 종료 날짜")
    private LocalDateTime endDate;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
//    @Schema(example = "스케쥴 상태코드")
    private Integer status;
}
