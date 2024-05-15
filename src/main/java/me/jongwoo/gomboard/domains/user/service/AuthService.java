package me.jongwoo.gomboard.domains.user.service;

import lombok.AllArgsConstructor;
import me.jongwoo.gomboard.domains.user.dto.UserDto;
import me.jongwoo.gomboard.domains.user.packet.JwtResponse;
import me.jongwoo.gomboard.domains.user.packet.LoginRequest;
import me.jongwoo.gomboard.domains.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final Duration tokenExpiration = Duration.ofMinutes(30);

    public JwtResponse login(LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new JwtResponse(createToken(user.toDto()));
    }

    private String createToken(UserDto userDto) {
        var claims = JwtClaimsSet.builder()
                .issuer("gomboard")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(tokenExpiration))
                .subject(userDto.email())
                .claim("user", userDto)
                .build();
        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(parameters).getTokenValue();
    }
}

