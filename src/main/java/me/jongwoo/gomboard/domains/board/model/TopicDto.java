package me.jongwoo.gomboard.domains.board.model;

import lombok.Builder;

@Builder
public record TopicDto(
        Long id,
        String name
) {
}
