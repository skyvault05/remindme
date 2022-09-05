package com.skyvault05.remindme.dto;

import com.skyvault05.remindme.domain.BaseTimeEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInListDto implements Serializable {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPicture;

}
