package no.item.play.connections.models;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusMessage {
    public final String id;
    public final String date;
    public final String message;
    public final Person person;
    public final String imageUrl;

    @JsonCreator
    public StatusMessage(@JsonProperty("id") String id,
                         @JsonProperty("date") String date,
                         @JsonProperty("message") String message,
                         @JsonProperty("person") Person person,
                         @JsonProperty("imageUrl") String imageUrl) {
        this.id = id;
        this.date = date;
        this.message = message;
        this.person = person;
        this.imageUrl = imageUrl;
    }
}
