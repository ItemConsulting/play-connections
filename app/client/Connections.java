package client;

import service.BlogService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Connections {
    private final BlogService blogs;

    @Inject
    public Connections(BlogService blogs) {
        this.blogs = blogs;
    }

    public BlogService blogs(){
        return blogs;
    }
}