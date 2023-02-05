package com.skyvault05.remindme.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    GUEST("ROLE_GUEST", "게스트"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

    public static UserRole of(String key) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.getKey().equals(key))
                .findAny()
                .orElse(GUEST);
    }
}
