package no.item.play.connections;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.clients.UnsecureWSClient;
import no.item.play.connections.clients.credentials.BasicCredentials;
import no.item.play.connections.clients.credentials.Credentials;
import org.slf4j.LoggerFactory;
import play.Configuration;
import play.libs.ws.WSClient;

public class TestModule extends AbstractModule {
    private Configuration config;

    public TestModule(Configuration config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        // WS Client
        bind(ConnectionsWSClient.class).to(UnsecureWSClient.class);

        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ERROR);
    }

    @Provides
    public Credentials getCredentials(){
        return new BasicCredentials(
                config.getString("connections.server.username"),
                config.getString("connections.server.password")
        );
    }
}