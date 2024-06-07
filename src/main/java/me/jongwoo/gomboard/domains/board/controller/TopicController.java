package me.jongwoo.gomboard.domains.board.controller;

import lombok.AllArgsConstructor;
import me.jongwoo.gomboard.common.response.ApiResponse;
import me.jongwoo.gomboard.domains.board.packet.TopicResponse;
import me.jongwoo.gomboard.domains.board.service.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topic")
@AllArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping()
    public ApiResponse<List<TopicResponse>> retrieveTopics() {
        return ApiResponse.success(
                topicService
                        .retrieveTopics()
                        .stream()
                        .map(topicDto -> TopicResponse.builder()
                                .id(topicDto.id())
                                .name(topicDto.name())
                                .build()
                        ).toList()
        );

    }
}
