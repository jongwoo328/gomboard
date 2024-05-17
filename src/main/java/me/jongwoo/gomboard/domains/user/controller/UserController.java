package me.jongwoo.gomboard.domains.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jongwoo.gomboard.common.response.ApiResponse;
import me.jongwoo.gomboard.domains.user.dto.UserDto;
import me.jongwoo.gomboard.domains.user.packet.RegisterUserRequest;
import me.jongwoo.gomboard.domains.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;


    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> registerUser(
            @RequestBody RegisterUserRequest registerUserRequest
    ) {
        UserDto userDto = userService.registerUser(registerUserRequest);
        return ResponseEntity.ok(
                ApiResponse.success(userDto)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> retrieveUsers() {
        var users = userService.retrieveUsers();
        return ResponseEntity.ok(
                ApiResponse.success(users)
        );
    }
}
