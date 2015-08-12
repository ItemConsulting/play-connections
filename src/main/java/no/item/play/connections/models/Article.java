package no.item.play.connections.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    public final String id;
    public final String title;
    public final String summary;
    public final String content;
    public final String url;
    public final LocalDateTime published;
    public final LocalDateTime updated;
    public final Integer hits;
    public final Integer recommendations;
    public final Integer comments;
    public final Optional<Person> author;

    @JsonCreator
    public Article(@JsonProperty("id") String id,
                   @JsonProperty("title") String title,
                   @JsonProperty("summary") String summary,
                   @JsonProperty("content") String content,
                   @JsonProperty("url") String url,
                   @JsonProperty("published") LocalDateTime published,
                   @JsonProperty("updated") LocalDateTime updated,
                   @JsonProperty("hits") Integer hits,
                   @JsonProperty("recommendations") Integer recommendations,
                   @JsonProperty("comments") Integer comments,
                   @JsonProperty("author")  Optional<Person> author) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.url = url;
        this.published = published;
        this.updated = updated;
        this.hits = hits;
        this.recommendations = recommendations;
        this.comments = comments;
        this.author = author;
    }
}
