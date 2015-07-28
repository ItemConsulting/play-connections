package no.item.play.connections;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.nerdforge.xml.ParserUtil;
import com.nerdforge.xml.XmlJsonMapperModule;
import com.nerdforge.xml.parsers.ArrayParser;
import no.item.play.connections.annotations.ServerUrl;
import play.Configuration;

public class ConnectionsModule extends AbstractModule implements Constants {
    private Configuration config;

    public ConnectionsModule(Configuration config){
        this.config = config;
    }

    @Override
    protected void configure() {
        install(new XmlJsonMapperModule(NAMESPACES));

        // Gets the server name
        bindConstant().annotatedWith(ServerUrl.class).to(config.getString("connections.server.host"));
    }

    @Provides
    @Named("articles")
    public ArrayParser articlesParser(Injector injector){
        ParserUtil util = injector.getInstance(ParserUtil.class);

        return util.arr("//a:entry", util.obj()
                .attribute("id", "a:id")
                .attribute("handle", "snx:handle")
                .attribute("title", "a:title"));
    }

    @Provides
    @Named("blogs")
    public ArrayParser blogsParser(Injector injector){
        ParserUtil util = injector.getInstance(ParserUtil.class);

        return util.arr("//a:entry", util.obj()
                .attribute("id", "a:id")
                .attribute("handle", "snx:handle")
                .attribute("title", "a:title"));
    }
}