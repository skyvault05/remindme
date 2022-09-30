package com.skyvault05.remindme.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SimpleUserDto {
    private Long id;
    private String nickname;
    private String picture;
    private Integer status;
}
