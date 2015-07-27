package no.item.play.connections.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo {
    public final String id;
    public final Person person;
    public final String name;
    public final String description;
    public final LocalDateTime date;

    public Todo(@JsonProperty("id") String id, @JsonProperty("person") Person person, @JsonProperty("name") String name,
                @JsonProperty("description") String description, @JsonProperty("date") LocalDateTime date) {
        this.id = id;
        this.person = person;
        this.name = name;
        this.description = description;
        this.date = date;
    }
}

