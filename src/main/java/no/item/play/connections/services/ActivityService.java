package no.item.play.connections.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.name.Named;
import com.nerdforge.xml.parsers.ArrayParser;
import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.clients.ConnectionsUtil;
import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.clients.ConnectionsWSRequest;
import no.item.play.connections.models.Atom;
import play.libs.ws.WSRequest;

import javax.inject.Inject;
import java.util.List;

public class ActivityService extends AbstractService {
    private ConnectionsWSClient client;
    private String serverUrl;
    private ArrayParser parser;

    @Inject
    public ActivityService(ConnectionsWSClient client, @ServerUrl String serverUrl, @Named("atoms") ArrayParser parser){
        this.client = client;
        this.serverUrl = serverUrl;
        this.parser = parser;
    }

    public ConnectionsWSRequest<List<Atom>> todos(){
        WSRequest request = client.url(serverUrl, PATH_CONNECTIONS_ACTIVITIES, "/service/atom2/todos");
        return ConnectionsUtil.builder(request, parser, typeref());

        /*return client.url(serverUrl, PATH_CONNECTIONS_ACTIVITIES, "/service/atom2/todos").get()
                .map(validateAndParseArray(parser));*/
    }

    @Override
    protected TypeReference<List<Atom>> typeref(){
        return new TypeReference<List<Atom>>() {};
    }
}
