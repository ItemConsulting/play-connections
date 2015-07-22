
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import no.item.play.connections.ConnectionsClient;
import no.item.play.connections.models.Blog;
import org.junit.Test;
import play.libs.F.Promise;
import util.TestModule;
import java.io.File;
import java.util.List;

import static org.fest.assertions.Fail.fail;

public class ConnectionsTest {
    private static final String PATH_TEST_CONF = "src/test/resources/conf/test.conf";

    @Test
    public void testConnection(){
        Config config = ConfigFactory.parseFile(new File(PATH_TEST_CONF)).resolve();
        final String server = config.getString("connections.server.host");

        Injector injector = Guice.createInjector(new TestModule(config));
        ConnectionsClient client = injector.getInstance(ConnectionsClient.class);
        Promise<List<Blog>> blogs = client.blogs().myBlogs();

        blogs.onFailure(t ->fail("Couldn't communicate with IBM Connections server [" + server + "]. Have you configured [secret.conf] or [test.conf]?"));

        blogs.get(5000L);
    }
}
