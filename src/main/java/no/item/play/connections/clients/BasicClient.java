package no.item.play.connections.clients;

import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

public class BasicClient implements WSClient {
    private final String username;
    private final String password;
    private WSClient client;

    public BasicClient(String username, String password){
        this(username, password,  WS.newClient(9000));
    }

    public BasicClient(String username, String password, WSClient client){
        this.username = username;
        this.password = password;
        this.client = client;
    }

    @Override
    public Object getUnderlying() {
        return client.getUnderlying();
    }

    @Override
    public WSRequest url(String url) {
        WSRequest request = client.url(url);
        request.setAuth(username, password);
        return request;
    }

    @Override
    public void close() {
        client.close();
    }
}
