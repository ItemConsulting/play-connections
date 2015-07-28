package no.item.play.connections;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.typesafe.config.ConfigFactory;
import no.item.play.connections.models.Blog;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Configuration;
import play.libs.F.Promise;
import play.libs.Json;

import java.io.File;
import java.util.List;

import static org.fest.assertions.Fail.fail;

public class ConnectionsClientTest {
    private static final File CONF_FILE = new File("src/test/resources/conf/test.conf");
    private static ConnectionsClient client;
    private static String host;

    @BeforeClass
    public static void setupBeforeClass(){
        Configuration config = new Configuration(ConfigFactory.parseFile(CONF_FILE).resolve());
        Injector injector = Guice.createInjector(Modules.override(new ConnectionsModule(config)).with(new TestModule(config)));

        host = config.getString("connections.server.host");
        client = injector.getInstance(ConnectionsClient.class);
    }

    @Test
    //@Ignore // Ignored by default, since it uses a live server
    public void testConnection(){
        Promise<List<Blog>> promise = client.blogs().myBlogs();
        promise.onFailure(t -> fail("Couldn't communicate with IBM Connections server [" + host + "]. Have you configured [secret.conf] or [test.conf]?"));
        List<Blog> blogs = promise.get(5000L);

        System.out.println(Json.toJson(blogs));
    }
}
