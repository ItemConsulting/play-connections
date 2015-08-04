package no.item.play.connections.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Atom {
    public final String id;
    public final String title;
    public final String subtitle;
    public final String summary;
    public final String content;
    public final LocalDateTime published;
    public final LocalDateTime updated;
    public final Person author;

    @JsonCreator
    public Atom(@JsonProperty("id") String id,
                @JsonProperty("title") String title,
                @JsonProperty("subtitle") String subtitle,
                @JsonProperty("summary") String summary,
                @JsonProperty("content") String content,
                @JsonProperty("published") LocalDateTime published,
                @JsonProperty("updated") LocalDateTime updated,
                @JsonProperty("author") Person author) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.summary = summary;
        this.content = content;
        this.published = published;
        this.updated = updated;
        this.author = author;
    }
}
