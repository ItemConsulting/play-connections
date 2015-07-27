package no.item.play.connections.services;
import com.nerdforge.xml.parsers.Parser;
import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.models.Blog;
import play.libs.F.Promise;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 * Service urls
 * ALL_BLOGS -> blogs/homepage/feed/blogs/atom
 * MY_BLOGS -> blogs/homepage/api/blogs
 * FEATURED_BLOGS -> blogs/homepage/feed/featuredblogs/atom
 * ALL_BLOG_POSTS -> blogs/homepage/feed/entries/atom
 * ALL_FEATURED_BLOG_POSTS -> blogs/homepage/feed/featured/atom
 * ALL_RECOMMENDED_BLOG_POSTS -> blogs/homepage/feed/recommended/atom
 * ALL_BLOG_COMMENTS -> blogs/homepage/feed/comments/atom
 * ALL_BLOG_TAGS -> blogs/homepage/feed/tags/atom
 * BLOG_POSTS ->    blogs/{handle}/feed/entries/atom
 * BLOG_COMMENTS -> blogs/{handle}/feed/comments/atom
 * BLOG_TAGS ->     blogs/{handle}/feed/tags/atom
 * CREATE_BLOG -> blogs/homepage/api/blogs
 * GET_UPDATE_REMOVE_BLOG -> blogs/homepage/api/blogs/handle
 * BLOG_POST -> ???
 * CREATE_BLOG_POST -> blogs/homepage/api/entries
 * UPDATE_REMOVE_POST -> blogs/homepage/api/entries/handle
 * CREATE_COMMENT -> blogs/homepage/api/comments/handle
 * GET_REMOVE_COMMENT -> blogs/homepage/api/comments/handle
 * BLOG_ENTRYCOMMENTS -> blogs/homepage/feed/entrycomments/handle/atom
 * RECOMMEND_POST -> blogs/homepage/api/recommend/entries/handle
 */
@Singleton
public class BlogService extends AbstractService {
    private ConnectionsWSClient client;
    private String serverUrl;
    private Parser parser;

    @Inject
    public BlogService(ConnectionsWSClient client, @ServerUrl String serverUrl, @Named("blogs") Parser parser){
        this.client = client;
        this.serverUrl = serverUrl;
        this.parser = parser;
    }

    public Promise<List<Blog>> myBlogs(){
        return client.url(serverUrl, PATH_CONNECTIONS_BLOGS, PATH_CONNECTIONS_HOMEPAGE, "/api/blogs").get()
                .map(validateAndParse(parser))
                .map(json -> toList(json, Blog.class));
    }

    public Promise<Blog> byId(){
        // todo do search
        return null;
    }
}
