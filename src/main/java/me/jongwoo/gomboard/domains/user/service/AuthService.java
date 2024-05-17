package me.jongwoo.gomboard.domains.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jongwoo.gomboard.domains.user.constant.JwtCustomClaimName;
import me.jongwoo.gomboard.domains.user.constant.JwtCustomClaimValue;
import me.jongwoo.gomboard.domains.user.constant.JwtType;
import me.jongwoo.gomboard.domains.user.dto.UserDto;
import me.jongwoo.gomboard.domains.user.packet.JwtRefreshRequest;
import me.jongwoo.gomboard.domains.user.packet.JwtResponse;
import me.jongwoo.gomboard.domains.user.packet.LoginRequest;
import me.jongwoo.gomboard.domains.user.repository.UserRepository;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final Duration tokenExpiration = Duration.ofMinutes(30);
    private final Duration refreshTokenExpiration = Duration.ofDays(30);

    public JwtResponse login(LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var token = createToken(user.toUserDto());
        var refreshToken = createRefreshToken(user.toUserDto());
        return JwtResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtResponse refresh(JwtRefreshRequest jwtRefreshRequest) {
        var jwt = jwtDecoder.decode(jwtRefreshRequest.refreshToken());
        if (!verifyRefreshToken(jwt)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        var userDto = UserDto.fromMap(jwt.getClaim(JwtCustomClaimName.USER));
        var newJwt = createRefreshToken(userDto);
        return JwtResponse.builder()
                .token(newJwt)
                .refreshToken(null)
                .build();
    }

    private boolean verifyRefreshToken(Jwt jwt) {
        boolean isTypeCorrect = jwt.getClaim(JwtCustomClaimName.TYPE).equals(JwtType.REFRESH);
        boolean isIssuerCorrect = jwt.getClaim(JwtClaimNames.ISS).equals(JwtCustomClaimValue.ISS);
        boolean isNotExpired = Instant.now().isBefore(Objects.requireNonNull(jwt.getExpiresAt()));

        UserDto jwtUser = UserDto.fromMap(jwt.getClaim(JwtCustomClaimName.USER));
        var user = userRepository.findById(jwtUser.id());
        boolean isUserCorrect = user.isPresent() && user.get().getId().equals(jwtUser.id())
                && user.get().getEmail().equals(jwtUser.email());

        return isTypeCorrect && isIssuerCorrect && isNotExpired && isUserCorrect;
    }

    private String createToken(UserDto userDto) {
        var claims = JwtClaimsSet.builder()
                .issuer(JwtCustomClaimValue.ISS)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(tokenExpiration))
                .subject(userDto.email())
                .claim(JwtCustomClaimName.USER, userDto)
                .claim(JwtCustomClaimName.TYPE, JwtType.ACCESS)
                .build();
        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(parameters).getTokenValue();
    }

    private String createRefreshToken(UserDto userDto) {
        var claims = JwtClaimsSet.builder()
                .issuer(JwtCustomClaimValue.ISS)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(refreshTokenExpiration))
                .subject(userDto.email())
                .claim(JwtCustomClaimName.USER, userDto)
                .claim(JwtCustomClaimName.TYPE, JwtType.REFRESH)
                .build();
        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(parameters).getTokenValue();
    }

}

