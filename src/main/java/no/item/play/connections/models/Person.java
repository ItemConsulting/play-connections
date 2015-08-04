package no.item.play.connections.models;

import com.fasterxml.jackson.annotation.*;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    public final String id;
    public final String name;
    public final Optional<String> email;
    public final Optional<String> jobTitle;
    public final Optional<String> telephone;

    @JsonCreator
    public Person(@JsonProperty("id") String id,
                  @JsonProperty("name") String name,
                  @JsonProperty("email") Optional<String> email,
                  @JsonProperty("jobTitle") Optional<String> jobTitle,
                  @JsonProperty("telephone") Optional<String> telephone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
        this.telephone = telephone;
    }
}
