package no.item.play.connections.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import no.item.play.connections.models.Person;
import play.libs.F.Promise;

import java.util.List;
import java.util.Optional;

public class PersonService {
    public Promise<Person> me(){
        //return Promise.pure(new Person("", Optional.<String>empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        return null;
    }

    public Promise<List<Person>> list(){
        return null;
    }
}
