package me.jongwoo.gomboard.domains.user.entity;

import jakarta.persistence.*;
import me.jongwoo.gomboard.common.entity.Auditable;

@Entity
@Table(name = "role")
public class Role extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
