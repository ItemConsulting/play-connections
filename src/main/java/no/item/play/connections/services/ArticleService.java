package no.item.play.connections.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.name.Named;
import com.nerdforge.xml.parsers.ArrayParser;
import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.models.Article;
import no.item.play.connections.models.Blog;
import no.item.play.connections.utils.Paths;
import play.libs.F.Promise;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleService extends AbstractService {
    private ConnectionsWSClient client;
    private String serverUrl;
    private ArrayParser parser;

    @Inject
    public ArticleService(ConnectionsWSClient client, @ServerUrl String serverUrl, @Named("articles") ArrayParser parser){
        this.client = client;
        this.serverUrl = serverUrl;
        this.parser = parser;
    }

    public Promise<List<Article>> list(String blogHandle){
        return client.url(serverUrl, PATH_CONNECTIONS_BLOGS, blogHandle, "/feed/entries/atom")
                .setQueryParameter("lang", "en").get()
                .map(validateAndParseArray(parser));
    }

    public Promise<Article> create(String blogHandle, Article article){
        return client.url(articleUrl(blogHandle)).post("")
                .map(res -> article);
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
