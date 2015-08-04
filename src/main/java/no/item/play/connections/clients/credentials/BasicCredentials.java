package no.item.play.connections.clients.credentials;

import play.Logger;
import play.libs.ws.WSRequest;

public class BasicCredentials implements Credentials {
    private Logger.ALogger logger = Logger.of("connections-play");
    private final String username;
    private final String password;

    public BasicCredentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public void decorate(WSRequest request) {
        logger.debug("Using BasicCredentials with username [{}]", username);
        request.setAuth(username, password);
    }
}
