package no.item.play.connections.factories;

import no.item.play.connections.models.Article;

import java.time.LocalDateTime;
import java.util.Optional;

public class ArticleFactory {
    public static Article create(String id, String title, String summary, String content, String url){
        return new Article(id, title, summary, content, url, LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, Optional.empty());
    }
}