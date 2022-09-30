package com.skyvault05.remindme.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SimpleScheduleDto {
    private Long id;
    private SimpleUserDto user;
    private String title;
    private String thumbnail;
    private Integer status;
}
