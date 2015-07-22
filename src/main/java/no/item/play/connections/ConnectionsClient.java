package no.item.play.connections;

import com.google.inject.Guice;
import com.google.inject.Injector;
import no.item.play.connections.services.BlogService;

import javax.inject.Inject;

public class ConnectionsClient {
    @Inject private BlogService blogs;

    public BlogService blogs(){
        return blogs;
    }

    public static ConnectionsClient getInstance(){
        Injector injector = Guice.createInjector(new ConnectionsModule());
        return injector.getInstance(ConnectionsClient.class);
    }
}
