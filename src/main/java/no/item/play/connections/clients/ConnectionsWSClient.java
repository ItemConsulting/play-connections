package no.item.play.connections.clients;

import no.item.play.connections.clients.credentials.Credentials;
import no.item.play.connections.utils.Paths;
import play.Logger;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import javax.inject.Provider;

public class ConnectionsWSClient implements WSClient {
    private Logger.ALogger logger = Logger.of("connections-play");
    private WSClient client;
    private Provider<Credentials> credentials;

    @Inject
    public ConnectionsWSClient(Provider<Credentials> credentials) {
        this(credentials, WS.newClient(9000));
    }

    public ConnectionsWSClient(Provider<Credentials> credentials, WSClient client){
        this.credentials = credentials;
        this.client = client;
    }

    @Override
    public Object getUnderlying() {
        return client.getUnderlying();
    }

    @Override
    public WSRequest url(String url){
        WSRequest request = client.url(url);
        credentials.get().decorate(request);
        return request;
    }

    public WSRequest url(String... urlParts) {
        String url = Paths.combineToUrl(urlParts);
        logger.debug("WSClient request to url=[{}]", url);
        return url(url);
    }

    @Override
    public void close() {
        client.close();
    }
}
