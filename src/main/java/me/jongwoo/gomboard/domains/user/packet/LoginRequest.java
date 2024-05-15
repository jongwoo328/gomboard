package me.jongwoo.gomboard.domains.user.packet;

public record LoginRequest(
        String email,
        String password
) {
}
