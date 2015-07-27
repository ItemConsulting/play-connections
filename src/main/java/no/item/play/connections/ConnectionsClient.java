package no.item.play.connections;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.ConfigFactory;
import no.item.play.connections.services.ArticleService;
import no.item.play.connections.services.BlogService;
import play.Configuration;

import javax.inject.Inject;
import java.util.Properties;

public class ConnectionsClient {
    @Inject private BlogService blogs;
    @Inject private ArticleService articles;

    public BlogService blogs(){
        return blogs;
    }

    public ArticleService articles(){
        return articles;
    }

    /**
     * Returns an instance of the client based on the properties. It is expected contain the following keys: { "connections.server.host", "connections.server.username", "connections.server.password" }
     * @param properties Properties to configure the instance of ConnectionsClient
     * @return A Connections client
     */
    public static ConnectionsClient getInstance(Properties properties){
        Injector injector = Guice.createInjector(new ConnectionsModule(config(properties)));
        return injector.getInstance(ConnectionsClient.class);
    }

    private static Configuration config(Properties properties){
        return new Configuration(ConfigFactory.parseProperties(properties));
    }
}
