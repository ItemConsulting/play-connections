package no.item.play.connections.services;

import com.google.inject.name.Named;
import com.nerdforge.xml.parsers.Parser;
import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.models.Article;
import no.item.play.connections.utils.Paths;
import play.libs.F.Promise;
import javax.inject.Inject;
import java.util.List;

public class ArticleService extends AbstractService {
    private ConnectionsWSClient client;
    private String serverUrl;
    private Parser parser;

    @Inject
    public ArticleService(ConnectionsWSClient client, @ServerUrl String serverUrl, @Named("articles") Parser parser){
        this.client = client;
        this.serverUrl = serverUrl;
        this.parser = parser;
    }

    public Promise<List<Article>> list(String blogHandle){
        return client.url(serverUrl, PATH_CONNECTIONS_BLOGS, blogHandle, "/feed/entries/atom").get()
                .map(validateAndParse(parser))
                .map(json -> toList(json, Article.class));
    }

    public Promise<Article> create(String blogHandle, Article article){
        return client.url(articleUrl(blogHandle)).post("")
                .map(res -> article);
    }

    public Promise<Boolean> delete(String blogHandle, String postUuid){
        return client.url(articleUrl(blogHandle), postUuid).delete().map(validateDeleted());
    }

    private String articleUrl(String blogHandle){
        return Paths.combineToUrl(serverUrl, PATH_CONNECTIONS_BLOGS, blogHandle, "/api/entries");
    }
}
