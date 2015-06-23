package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog {
    public String id;
    public String handle;
    public String title;
    public Optional<Person> author;
    public String url;
}
