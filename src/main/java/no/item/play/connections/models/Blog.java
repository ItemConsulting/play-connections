package no.item.play.connections.models;

import com.fasterxml.jackson.annotation.*;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog {
    public final String id;
    public final String handle;
    public final String title;
    public final Optional<Person> author;

    @JsonCreator
    public Blog(@JsonProperty("id") String id,
                @JsonProperty("handle") String handle,
                @JsonProperty("title") String title,
                @JsonProperty("author") Optional<Person> author){
        this.id = id;
        this.handle = handle;
        this.title = title;
        this.author = author;
    }
}
