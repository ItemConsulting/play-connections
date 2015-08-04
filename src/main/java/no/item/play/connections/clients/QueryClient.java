package no.item.play.connections.clients;

import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

import javax.inject.Inject;

public class QueryClient implements WSClient {
    private ConnectionsWSClient client;

    @Inject
    public QueryClient(ConnectionsWSClient client){
        this.client = client;
    }

    @Override
    public Object getUnderlying() {
        return client.getUnderlying();
    }

    @Override
    public WSRequest url(String url) {
        return client.url(url);
    }

    @Override
    public void close() {
        client.close();
    }
}
