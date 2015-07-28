package no.item.play.connections.services;

import com.google.inject.name.Named;
import com.nerdforge.xml.parsers.ArrayParser;
import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.models.Todo;
import play.libs.F.Promise;

import javax.inject.Inject;
import java.util.List;

public class ActivityService extends AbstractService {
    private ConnectionsWSClient client;
    private String serverUrl;
    private ArrayParser parser;

    @Inject
    public ActivityService(ConnectionsWSClient client, @ServerUrl String serverUrl, @Named("activites") ArrayParser parser){
        this.client = client;
        this.serverUrl = serverUrl;
        this.parser = parser;
    }

    public Promise<List<Todo>> todos(){
        return client.url(serverUrl, PATH_CONNECTIONS_ACTIVITIES, "/service/atom2/todos").get()
                .map(validateAndParseArray(parser, Todo.class));
    }
}
