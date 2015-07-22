package no.item.play.connections.services;
import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.models.Blog;
import no.item.play.connections.parsers.Parsers;
import play.libs.F.Promise;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class BlogService extends AbstractService {
    private WSClient client;
    private String serverUrl;

    @Inject
    public BlogService(WSClient client,@ServerUrl String serverUrl){
        this.client = client;
        this.serverUrl = serverUrl;
    }

    public Promise<List<Blog>> myBlogs(){
        return client.url(serverUrl + "/blogs/homepage/api/blogs").get()
                .map(this::validate)
                .map(WSResponse::asXml)
                .map(Parsers::blogs);
    }
}
