package me.jongwoo.gomboard.domains.board.entity;

import jakarta.persistence.*;
import me.jongwoo.gomboard.common.entity.Auditable;

import java.util.UUID;

@Entity
@Table(name = "article")
public class Article extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @JoinColumn(name = "topic_id", nullable = false)
    private Long topicId;

    @JoinColumn(name = "user_id", nullable = false)
    private UUID userId;
}
