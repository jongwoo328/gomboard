package me.jongwoo.gomboard.domains.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jongwoo.gomboard.common.response.CommonResponse;
import me.jongwoo.gomboard.domains.user.dto.UserDto;
import me.jongwoo.gomboard.domains.user.packet.RegisterUserRequest;
import me.jongwoo.gomboard.domains.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonResponse> registerUser(
            @RequestBody RegisterUserRequest registerUserRequest
    ) {
        UserDto userDto = userService.registerUser(registerUserRequest);
        log.debug("TEST:userDto = {}", userDto);
        var response = new CommonResponse("success", userDto);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<CommonResponse> retrieveUsers() {
        var users = userService.retrieveUsers();
        return ResponseEntity.ok(
                new CommonResponse("success", users)
        );
    }
}
