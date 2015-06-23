package client;

import endpoint.ConnectionsEndpoint;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import javax.inject.Provider;

public class ConnectionsClient {

    @Inject
    Provider<ConnectionsEndpoint> endpoint;

    public Promise<WSResponse> get(String path){
        return WS.url(buildUrl(path))
                .setAuth(endpoint.get().username, endpoint.get().password)
                .get();
    }

    private String buildUrl(String path){
        return endpoint.get().url + path;

    }
}
