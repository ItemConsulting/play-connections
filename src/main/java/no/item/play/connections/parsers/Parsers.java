package no.item.play.connections.parsers;

import no.item.play.connections.models.Blog;
import org.w3c.dom.Document;
import java.util.List;

public class Parsers {
    private static Parser AUTHOR_PARSER  = Parser.list("")
            .attribute("id", "a:author/snx:userid")
            .attribute("name", "a:author/a:name")
            .attribute("email", "a:author/a:email")
            .build();

    private static Parser BLOG_PARSER  = Parser.list("/a:feed/a:entry")
            .attribute("id", "a:id", Parsers::parseId)
            .attribute("handle", "./snx:handle")
            .attribute("title", "a:title")
            .object("author", AUTHOR_PARSER)
            .build();

    public static List<Blog> blogs(Document document) {
        return BLOG_PARSER.parse(document, Blog.class);
    }

    public static String parseId(String id){
        return id;
    }
}
