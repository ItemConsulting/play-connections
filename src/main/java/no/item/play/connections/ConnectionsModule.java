package no.item.play.connections;

import com.google.inject.AbstractModule;
import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.clients.credentials.BasicCredentials;
import no.item.play.connections.clients.credentials.Credentials;
import play.Configuration;
import play.libs.ws.WSClient;

public class ConnectionsModule extends AbstractModule {
    private Configuration config;

    public ConnectionsModule(Configuration config){
        this.config = config;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(ServerUrl.class).to(config.getString("connections.server.host"));

        Credentials basicCredentials = new BasicCredentials(
            config.getString("connections.server.username"),
            config.getString("connections.server.password")
        );

        bind(WSClient.class).to(ConnectionsWSClient.class);
    }
}