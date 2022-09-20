package com.skyvault05.remindme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skyvault05.remindme.utils.exceptions.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @Schema(example = "유저 이름")
    private String name;
    @Schema(example = "유저 이메일")
    private String email;
    @Schema(example = "프로필 사진 주소")
    private String picture;
    @Schema(example = "유저 친구목록")
    private List<UserDto> friends;
    @Schema(example = "유저 역할")
    private UserRole role;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime modifiedDate;
    @Schema(example = "유저 상태코드")
    private Integer status;
}
