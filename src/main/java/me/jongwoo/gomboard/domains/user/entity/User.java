package me.jongwoo.gomboard.domains.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jongwoo.gomboard.domains.user.dto.UserDto;

import java.util.UUID;

@Builder
@Getter
@Entity
@Table(name = "`user`")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id", length = 36, unique = true)
    private UUID id;
    @Column(name = "email", length = 100, unique = true)
    private String email;
    @Column(name = "password", length = 100)
    private String password;
    private String refreshToken;

    public UserDto toUserDto() {
        return new UserDto(id, email);
    }
}
