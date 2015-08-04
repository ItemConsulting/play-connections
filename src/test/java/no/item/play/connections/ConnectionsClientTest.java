package no.item.play.connections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.typesafe.config.ConfigFactory;
import no.item.play.connections.models.Article;
import no.item.play.connections.models.Blog;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import play.Configuration;
import play.libs.F.Promise;
import play.libs.Json;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.fest.assertions.Fail.fail;

public class ConnectionsClientTest {
    private static final File CONF_FILE = new File("src/test/resources/conf/test.conf");
    private static ConnectionsClient client;
    private static String host;
    private static ObjectMapper mapper;

    @BeforeClass
    public static void setupBeforeClass(){
        Configuration config = new Configuration(ConfigFactory.parseFile(CONF_FILE).resolve());
        Injector injector = Guice.createInjector(Modules.override(new ConnectionsModule(config)).with(new TestModule(config)));

        host = config.getString("connections.server.host");
        client = injector.getInstance(ConnectionsClient.class);
        mapper = injector.getInstance(ObjectMapper.class);
    }

    @Test
    //@Ignore // Ignored by default, since it uses a live server
    public void testBlogs(){
        Promise<List<Blog>> promise = client.blogs().myBlogs();
        promise.onFailure(t -> fail("Couldn't communicate with IBM Connections server [" + host + "]. Have you configured [secret.conf] or [test.conf]?"));
        List<Blog> blogs = promise.get(5000L);

        blogs.stream().forEach(blog -> System.out.println(Json.toJson(blog)));
    }

    @Test
    //@Ignore // Ignored by default, since it uses a live server
    public void testBlog(){
        Promise<Blog> promise = client.blogs().byId("urn:lsid:ibm.com:blogs:blog-da688f60-17d8-481d-a1ac-e00010040e49");
        promise.onFailure(t -> fail("Couldn't communicate with IBM Connections server [" + host + "]. Have you configured [secret.conf] or [test.conf]?"));
        Blog blog = promise.get(5000L);

        System.out.println(Json.toJson(blog));
    }

    @Test
    //@Ignore // Ignored by default, since it uses a live server
    public void testArticles() throws JsonProcessingException {
        Promise<List<Article>> promise = client.articles().list("tomtime");
        promise.onFailure(t -> fail("Couldn't communicate with IBM Connections server [" + host + "]. Have you configured [secret.conf] or [test.conf]?"));
        List<Article> result = promise.get(5000L);

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result.get(0)));
    }
}
