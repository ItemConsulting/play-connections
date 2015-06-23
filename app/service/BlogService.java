package service;

import client.ConnectionsClient;
import models.Blog;
import parser.Parser;
import play.libs.F.Promise;

import javax.inject.Inject;
import java.util.List;

public class BlogService {
    private final ConnectionsClient client;

    @Inject
    public BlogService(ConnectionsClient client) {
        this.client = client;
    }

    public Promise<List<Blog>> list(){
        Parser parser = Parser.list("//mapping/blog")
                .attribute("id", "@id")
                .build();

        return client.get("/blogpth")
                .map(res -> parser.parse(res.asXml(), Blog.class));
    }
}
