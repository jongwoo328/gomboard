package me.jongwoo.gomboard.domains.board.repository;

import me.jongwoo.gomboard.domains.board.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
