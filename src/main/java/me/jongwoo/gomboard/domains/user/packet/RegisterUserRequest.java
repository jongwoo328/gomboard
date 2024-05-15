package me.jongwoo.gomboard.domains.user.packet;

public record RegisterUserRequest(
        String email,
        String password
) {
}
