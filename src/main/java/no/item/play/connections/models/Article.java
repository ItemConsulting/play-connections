package no.item.play.connections.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Optional;

public class Article {
    public final String id;
    public final String title;
    public final String summary;
    @JsonIgnore
    public final String content;
    public final String url;
    public final Optional<LocalDateTime> date;
    public final Integer hitCount;
    public final Integer recommendationsCount;
    public final String imageURL;
    public Blog blog;
    
    public Article(final String id, final String title, final String url, final String summary, final String content, final Optional<LocalDateTime> date,
                   final Integer hitCount, final Integer recommendationsCount, final String imageURL, final Blog blog){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.url = url;
        this.date = date;
        this.hitCount = hitCount;
        this.recommendationsCount = recommendationsCount;
        this.imageURL = imageURL;
        this.blog = blog;
    }
}
