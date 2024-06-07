package me.jongwoo.gomboard.domains.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jongwoo.gomboard.common.entity.Auditable;
import me.jongwoo.gomboard.domains.user.model.UserDto;
import me.jongwoo.gomboard.domains.user.provider.UUIDv7Provider;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Entity
@Table(name = "`user`")
@AllArgsConstructor
@NoArgsConstructor
public class User extends Auditable {
    @Id
    @Column(name = "id", length = 36, unique = true)
    private UUID id;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @PrePersist
    protected void generateId() {
        if (this.id == null) {
            this.id = UUIDv7Provider.getUUIDv7();
        }
    }

    public UserDto toUserDto() {
        return new UserDto(id, email);
    }
}
