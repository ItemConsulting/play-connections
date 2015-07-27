package no.item.play.connections;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import no.item.play.connections.models.Blog;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import play.libs.F.Promise;
import util.TestModule;

import java.io.File;
import java.util.List;

import static org.fest.assertions.Fail.fail;

public class ConnectionsClientTest {
    private static final File CONF_FILE = new File("src/test/resources/conf/test.conf");
    private static ConnectionsClient client;
    private static String host;

    @BeforeClass
    public static void setupBeforeClass(){
        Config config = ConfigFactory.parseFile(CONF_FILE).resolve();
        Injector injector = Guice.createInjector(new TestModule(config));

        host = config.getString("connections.server.host");
        client = injector.getInstance(ConnectionsClient.class);
    }

    @Test
    @Ignore // Ignored by default, since it uses a live server
    public void testConnection(){
        Promise<List<Blog>> blogs = client.blogs().myBlogs();
        blogs.onFailure(t ->fail("Couldn't communicate with IBM Connections server [" + host + "]. Have you configured [secret.conf] or [test.conf]?"));
        blogs.get(5000L);
    }
}
