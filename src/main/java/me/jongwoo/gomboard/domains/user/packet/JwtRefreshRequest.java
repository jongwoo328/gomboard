package me.jongwoo.gomboard.domains.user.packet;

public record JwtRefreshRequest(
        String refreshToken
) {
}
