package no.item.play.connections.clients.credentials;

import play.libs.ws.WSRequest;

public interface Credentials {
    void decorate(WSRequest request);
}
