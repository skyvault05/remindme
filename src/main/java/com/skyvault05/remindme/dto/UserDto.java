package com.skyvault05.remindme.dto;

import com.skyvault05.remindme.domain.BaseTimeEntity;
import com.skyvault05.remindme.domain.UserRole;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPicture;
    private List<UserInListDto> userFriend;
    private UserRole userRole;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer userStatus;
}
