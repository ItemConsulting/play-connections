package no.item.play.connections.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Atom {
    public final String id;
    public final String title;

    @JsonCreator
    public Atom(@JsonProperty("id") String id, @JsonProperty("title") String title) {
        this.id = id;
        this.title = title;
    }
}
