package me.jongwoo.gomboard.domains.user.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String email
) {
}
