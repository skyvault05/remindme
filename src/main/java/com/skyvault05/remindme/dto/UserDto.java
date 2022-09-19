package com.skyvault05.remindme.dto;

import com.skyvault05.remindme.utils.exceptions.enums.UserRole;
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
    private String name;
    private String email;
    private String picture;
    private List<UserDto> friends;
    private UserRole role;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer status;
}
