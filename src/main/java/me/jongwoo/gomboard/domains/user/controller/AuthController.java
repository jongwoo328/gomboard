package me.jongwoo.gomboard.domains.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jongwoo.gomboard.domains.user.packet.JwtResponse;
import me.jongwoo.gomboard.domains.user.packet.LoginRequest;
import me.jongwoo.gomboard.domains.user.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
