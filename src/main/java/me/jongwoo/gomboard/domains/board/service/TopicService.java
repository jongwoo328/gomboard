package me.jongwoo.gomboard.domains.board.service;

import lombok.AllArgsConstructor;
import me.jongwoo.gomboard.domains.board.model.TopicDto;
import me.jongwoo.gomboard.domains.board.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    public List<TopicDto> retrieveTopics() {
        return topicRepository.findAll()
                .stream()
                .map(topic ->
                        TopicDto.builder()
                                .id(topic.getId())
                                .name(topic.getName())
                                .build()
                ).toList();
    }
}
