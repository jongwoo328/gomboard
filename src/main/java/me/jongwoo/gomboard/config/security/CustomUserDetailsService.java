package me.jongwoo.gomboard.config.security;

import lombok.AllArgsConstructor;
import me.jongwoo.gomboard.domains.user.dto.UserAuthDto;
import me.jongwoo.gomboard.domains.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        var user = userRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserAuthDto userAuthDto = new UserAuthDto(user.getId().toString(), user.getPassword());
        return new CustomUserDetails(userAuthDto);
    }
}
