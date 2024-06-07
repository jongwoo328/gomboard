package me.jongwoo.gomboard.domains.board.service;

import lombok.AllArgsConstructor;
import me.jongwoo.gomboard.domains.board.packet.TopicResponse;
import me.jongwoo.gomboard.domains.board.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    public List<TopicResponse> retrieveTopics() {
        return topicRepository.findAll()
                .stream()
                .map(topic ->
                        TopicResponse.builder()
                                .id(topic.getId())
                                .name(topic.getName())
                                .build()
                ).toList();
    }
}
