package me.jongwoo.gomboard.domains.board.packet;

import lombok.Builder;

@Builder
public record TopicResponse(
        Long id,
        String name
) {

}
