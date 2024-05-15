package me.jongwoo.gomboard.domains.user.packet;

import lombok.Builder;

@Builder
public record JwtResponse(
        String token,
        String refreshToken
) {
}
