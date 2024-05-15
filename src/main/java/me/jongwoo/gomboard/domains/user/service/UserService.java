package me.jongwoo.gomboard.domains.user.service;

import lombok.AllArgsConstructor;
import me.jongwoo.gomboard.domains.user.dto.UserDto;
import me.jongwoo.gomboard.domains.user.entity.User;
import me.jongwoo.gomboard.domains.user.packet.RegisterUserRequest;
import me.jongwoo.gomboard.domains.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto registerUser(RegisterUserRequest registerUserDto) {
        var user = User.builder()
                .id(UUID.randomUUID())
                .email(registerUserDto.email())
                .password(passwordEncoder.encode(registerUserDto.password()))
                .build();

        var savedUser = userRepository.save(user);
        return savedUser.toDto();
    }

    public List<UserDto> retrieveUsers() {
        return userRepository.findAll().stream().map(User::toDto).toList();
    }
}
