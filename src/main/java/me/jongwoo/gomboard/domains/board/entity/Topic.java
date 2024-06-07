package me.jongwoo.gomboard.domains.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import me.jongwoo.gomboard.common.entity.Auditable;

@Entity
@Getter
@Table(name = "topic")
public class Topic extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

}
