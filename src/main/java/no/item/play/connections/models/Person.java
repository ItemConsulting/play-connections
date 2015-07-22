package no.item.play.connections.models;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    public final String id;
    public final String name;
    public final String email;

    @JsonCreator
    public Person(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("email") String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
