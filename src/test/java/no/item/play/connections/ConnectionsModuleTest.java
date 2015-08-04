package no.item.play.connections;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.nerdforge.xml.parsers.ArrayParser;
import static com.nerdforge.xml.Parsers.document;
import com.typesafe.config.ConfigFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import play.Configuration;

import java.io.File;

public class ConnectionsModuleTest {
    private static final File CONF_FILE = new File("src/test/resources/conf/test.conf");
    private static ArrayParser blogsParser;
    private static ArrayParser articlesParser;

    @BeforeClass
    public static void setupBeforeClass(){
        Configuration config = new Configuration(ConfigFactory.parseFile(CONF_FILE).resolve());
        Injector injector = Guice.createInjector(Modules.override(new ConnectionsModule(config)).with(new TestModule(config)));

        blogsParser = injector.getInstance(Key.get(ArrayParser.class, Names.named("blogs")));
        articlesParser = injector.getInstance(Key.get(ArrayParser.class, Names.named("articles")));
    }

    @Test
    public void testBlogsParser(){
        Document document = document(new File("src/test/resources/xml/blogs-my.xml"));
        ArrayNode result = blogsParser.apply(document);
        // TODO
    }

    @Test
    public void testArticlesParser(){
        Document document = document(new File("src/test/resources/xml/articles.xml"));
        ArrayNode result = articlesParser.apply(document);
        // TODO
    }
}
