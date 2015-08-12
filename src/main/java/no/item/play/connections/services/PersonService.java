package no.item.play.connections.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.models.Person;
import play.libs.F.Promise;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

public class PersonService {
    private ConnectionsWSClient client;
    private String serverUrl;
    private ImageService imageService;

    @Inject
    public PersonService(ConnectionsWSClient client, @ServerUrl String serverUrl, ImageService imageService){
        this.client = client;
        this.serverUrl = serverUrl;
        this.imageService = imageService;
    }

    public Promise<Person> me(){
        //return Promise.pure(new Person("", Optional.<String>empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        return null;
    }

    public Promise<List<Person>> list(){
        return null;
    }

    public Promise<BufferedImage> image(String userId){
        return client.url(serverUrl, "/profiles/photo.do")
                .setQueryParameter("userid", userId)
                .get().map(imageService::parseResponse);
    }
}
