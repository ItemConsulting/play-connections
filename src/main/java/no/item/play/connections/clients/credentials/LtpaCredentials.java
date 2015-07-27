package no.item.play.connections.clients.credentials;

import play.libs.ws.WSRequest;

public class LtpaCredentials implements Credentials {
    private final String ltpaToken;

    public LtpaCredentials(String ltpaToken){
        this.ltpaToken = ltpaToken;
    }

    @Override
    public void decorate(WSRequest request) {
        request.setHeader("Set-Cookie", String.format("LtpaToken=%s", ltpaToken));
    }
}
