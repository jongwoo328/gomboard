package me.jongwoo.gomboard.domains.board.repository;

import me.jongwoo.gomboard.domains.board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
