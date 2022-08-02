package com.skyvault05.remindme.dto;

import com.skyvault05.remindme.domain.BaseTimeEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInListDto {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPicture;
    private Integer userStatus;
}
