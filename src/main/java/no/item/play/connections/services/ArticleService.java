package no.item.play.connections.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.name.Named;
import com.nerdforge.xml.parsers.ArrayParser;
import com.nerdforge.xml.parsers.ObjectParser;
import no.item.play.connections.WSException;
import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.models.Article;
import no.item.play.connections.utils.AtomBulider;
import no.item.play.connections.utils.Paths;
import no.item.play.connections.utils.XPaths;
import play.Logger;
import play.libs.F.Promise;
import scala.concurrent.ExecutionContext;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleService extends AbstractService {
    private ConnectionsWSClient client;
    private String serverUrl;
    private ArrayParser listParser;
    private ObjectParser parser;

    @Inject
    public ArticleService(ConnectionsWSClient client,
                          @ServerUrl String serverUrl,
                          @Named("articles") ArrayParser listParser,
                          @Named("article") ObjectParser parser){
        this.client = client;
        this.serverUrl = serverUrl;
        this.listParser = listParser;
        this.parser = parser;
    }

    public Promise<Article> get(String blogHandle, String articleId){
        return client.url(serverUrl, PATH_CONNECTIONS_BLOGS, blogHandle, "/feed/entry/atom")
                .setQueryParameter("entryid", articleId)
                .setQueryParameter("lang", "no").get()
                .map(validateAndParse(parser, Article.class));
    }

    public Promise<List<Article>> list(String blogHandle){
        return client.url(serverUrl, PATH_CONNECTIONS_BLOGS, blogHandle, "/feed/entries/atom")
                .setQueryParameter("lang", "en").get()
                .map(validateAndParseArray(listParser));
    }

    public Promise<Article> create(String blogHandle, Article article){
        return client.url(articleUrl(blogHandle))
                .setHeader("Content-Type", "application/atom+xml")
                .post(AtomBulider.create(article))
                .map(validateAndParse(parser, Article.class));
    }

    public Promise<List<Article>> create(String blogHandle, List<Article> articles){
        List<Promise<Article>> promises = articles.stream()
                .map(article -> create(blogHandle, article))
                .collect(Collectors.toList());

        return Promise.sequence(promises);
    }

    public Promise<Boolean> delete(String blogHandle, String postUuid){
        return client.url(articleUrl(blogHandle), postUuid).delete().map(validateDeleted());
    }

    private String articleUrl(String blogHandle){
        return Paths.combineToUrl(serverUrl, PATH_CONNECTIONS_BLOGS, blogHandle, "/api/entries");
    }

    @Override
    protected TypeReference<List<Article>> typeref(){
        return new TypeReference<List<Article>>() {};
    }
}
