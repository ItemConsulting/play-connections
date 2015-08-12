package no.item.play.connections;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.ibm.commons.xml.DOMUtil;
import com.ibm.commons.xml.XMLException;
import com.ibm.commons.xml.xpath.XPathExpression;
import com.ibm.sbt.services.client.base.datahandlers.FieldEntry;
import com.nerdforge.xml.ParserUtil;
import com.nerdforge.xml.XmlJsonMapperModule;
import com.nerdforge.xml.parsers.ArrayParser;
import com.nerdforge.xml.parsers.ObjectParser;
import no.item.play.connections.annotations.ServerUrl;
import play.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    /*
        singleCategory("/a:category"),
        contributor("./a:contributor"),
        modifier("./td:modifier"),
        categoryTerm("./a:category/@term"),
        category("/a:category"),
        content("./a:content"),
        repliesUrl("./a:link[@rel='replies']/@href"),
        editUrl("./a:link[@rel='edit']/@href"),
        selfUrl("./a:link[@rel='self']/@href"),
        nextUrl("./a:link[@rel='next']/@href"),
        alternateUrl("./a:link[@rel='alternate']/@href"),
        tags("./a:category[not(@scheme)]/@term"),
        */

    @Provides
    @Named("atoms")
    public ArrayParser atomParser(Injector injector){
        ParserUtil util = injector.getInstance(ParserUtil.class);
        return util.arr("/a:feed/a:entry", util.obj()
            .attribute("id", "./a:id")
            .attribute("title", "./a:title")
            .attribute("published", "a:published", util.with(str -> LocalDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
            .attribute("summary", "a:summary")
            .attribute("content", "a:content")
            .attribute("subtitle", "a:subtitle")
            .attribute("updated", "a:updated", util.with(str -> LocalDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
            .attribute("author", "./a:author", util.obj()
                .attribute("id", "snx:userid")
                .attribute("name", "a:name")
                .attribute("email", "a:email")
                .attribute("state", "./snx:userState")
            )
        );
    }


    @Provides
    @Named("activites")
    public ArrayParser activitiesParser(Injector injector){
        ParserUtil util = injector.getInstance(ParserUtil.class);
        return util.arr("//a:entry");
    }


    @Provides
    @Named("articles")
    public ArrayParser articlesParser(Injector injector){
        ParserUtil util = injector.getInstance(ParserUtil.class);
        return util.arr("//a:entry", articleParser(injector));
    }

    @Provides
    @Named("article")
    public ObjectParser articleParser(Injector injector){
        ParserUtil util = injector.getInstance(ParserUtil.class);

        return util.obj()
            .attribute("id", "a:id")
            .attribute("title", "a:title")
            .attribute("author", "a:author", util.obj()
                            .attribute("id", "snx:userid")
                            .attribute("name", "a:name")
                            .attribute("email", "a:email")
            )
            .attribute("summary", "a:summary")
            .attribute("content", "a:content")
            .attribute("published", "a:published", util.with(str -> LocalDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
            .attribute("updated", "a:updated", util.with(str -> LocalDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
            .attribute("url", "a:link[@rel='self']/@href")
            .attribute("hits", xpathRank("hit"), util.with(Integer::parseInt))
            .attribute("recommendations", xpathRank("recommendations"), util.with(Integer::parseInt))
            .attribute("comments", xpathRank("comment"), util.with(Integer::parseInt)).build();
    }

    @Provides
    @Named("blogs")
    public ArrayParser blogsParser(Injector injector) {
        ParserUtil util = injector.getInstance(ParserUtil.class);

        return util.arr("//a:entry", util.obj()
            .attribute("id", "a:id")
            .attribute("handle", "snx:handle")
            .attribute("title", "a:title")
            .attribute("author", "a:author", util.obj()
                .attribute("id", "snx:userid")
                .attribute("name", "a:name")
                .attribute("email", "a:email")
            )
        );
    }
}