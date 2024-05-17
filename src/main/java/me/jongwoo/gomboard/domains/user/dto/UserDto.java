package me.jongwoo.gomboard.domains.user.dto;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        String email
) {
    public static UserDto fromMap(Map map) {
        String id = (String) map.get("id");
        String email = (String) map.get("email");
        if (id == null || email == null) {
            throw new IllegalArgumentException("id and email must not be null");
        }
        return new UserDto(UUID.fromString(id), email);
    }
}
