package no.item.play.connections.clients.credentials;

import play.libs.ws.WSRequest;

public class BasicCredentials implements Credentials {
    private final String username;
    private final String password;

    public BasicCredentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public void decorate(WSRequest request) {
        request.setAuth(username, password);
    }
}
