package no.item.play.connections;

import com.google.inject.Guice;
import com.ibm.sbt.services.client.connections.blogs.BlogPost;
import com.ibm.sbt.services.client.connections.blogs.BlogService;
import com.ibm.sbt.services.client.connections.blogs.serializers.BlogPostSerializer;
import com.ibm.sbt.services.endpoints.BasicEndpoint;
import com.ibm.sbt.services.endpoints.Endpoint;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Testing {


    @Test
    public void test(){
        String x = "<?xml version=\"1.0\"?><entry xml:lang=\"en-US\" dir=\"ltr\" xmlns=\"http://www.w3.org/2005/Atom\"><id>urn:lsid:ibm.com:blogs:entry-7936364e-5c40-4a75-8c7e-559019796b3d</id><link href=\"https://teamup.item.no:443/blogs/tomtime/api/entries/7936364e-5c40-4a75-8c7e-559019796b3d\" rel=\"edit\" type=\"application/atom+xml\"></link><link href=\"https://teamup.item.no/blogs/tomtime/entry/Etterforskere_vil_hellerjobbe_med_narkotika_ogorganisert_kriminalitet1\" rel=\"alternate\" type=\"text/html\"></link><link href=\"https://teamup.item.no:443/blogs/tomtime/feed/entrycomments/Etterforskere_vil_hellerjobbe_med_narkotika_ogorganisert_kriminalitet1/atom\" rel=\"replies\" type=\"application/atom+xml\" thr:count=\"0\" xmlns:thr=\"http://purl.org/syndication/thread/1.0\"></link><app:collection href=\"https://teamup.item.no:443/blogs/tomtime/api/recommend/entries/7936364e-5c40-4a75-8c7e-559019796b3d\" xmlns:app=\"http://www.w3.org/2007/app\"><atom:title xmlns:atom=\"http://www.w3.org/2005/Atom\">Likes</atom:title><atom:category term=\"recommend\" scheme=\"http://www.ibm.com/xmlns/prod/sn/collection\" xmlns:atom=\"http://www.w3.org/2005/Atom\"></atom:category><app:categories fixed=\"yes\"></app:categories></app:collection><app:collection href=\"https://teamup.item.no:443/blogs/tomtime/api/entrycomments/7936364e-5c40-4a75-8c7e-559019796b3d\" xmlns:app=\"http://www.w3.org/2007/app\"><atom:title xmlns:atom=\"http://www.w3.org/2005/Atom\">Comment Entries</atom:title><app:accept>application/atom+xml;type=entry</app:accept><atom:category term=\"comments\" scheme=\"http://www.ibm.com/xmlns/prod/sn/collection\" xmlns:atom=\"http://www.w3.org/2005/Atom\"></atom:category><app:categories fixed=\"yes\"></app:categories></app:collection><snx:moderation status=\"approved\" xmlns:snx=\"http://www.ibm.com/xmlns/prod/sn\"></snx:moderation><title type=\"text\">– Etterforskere vil hellerjobbe med narkotika ogorganisert kriminalitet</title><updated>2015-08-10T10:57:12.000Z</updated><app:edited xmlns:app=\"http://www.w3.org/2007/app\">2015-08-10T10:57:12.000Z</app:edited><published>2015-08-10T10:57:12.000Z</published><snx:rank scheme=\"http://www.ibm.com/xmlns/prod/sn/recommendations\" xmlns:snx=\"http://www.ibm.com/xmlns/prod/sn\">0</snx:rank><snx:rank scheme=\"http://www.ibm.com/xmlns/prod/sn/comment\" xmlns:snx=\"http://www.ibm.com/xmlns/prod/sn\">0</snx:rank><snx:rank scheme=\"http://www.ibm.com/xmlns/prod/sn/hit\" xmlns:snx=\"http://www.ibm.com/xmlns/prod/sn\">0</snx:rank><author><name>Tom Arild Jakobsen</name><email>tom@item.no</email><snx:userid xmlns:snx=\"http://www.ibm.com/xmlns/prod/sn\">79B4725A-C0F0-05D3-C125-79AC00413594</snx:userid><snx:userState xmlns:snx=\"http://www.ibm.com/xmlns/prod/sn\">active</snx:userState><snx:isExternal xmlns:snx=\"http://www.ibm.com/xmlns/prod/sn\">false</snx:isExternal></author><app:control xmlns:app=\"http://www.w3.org/2007/app\"><app:draft>no</app:draft><snx:comments enabled=\"yes\" days=\"0\" xmlns:snx=\"http://www.ibm.com/xmlns/prod/sn\"></snx:comments></app:control><summary type=\"html\"></summary><content type=\"html\"></content></entry>\n";

        Guice.createInjector(new ConnectionsModule());



        util.obj()
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

        /*Endpoint endpoint = new BasicEndpoint();

        BlogService service = new BlogService(endpoint);

        BlogPost post = new BlogPost(service, "id");
        post.setBlogHandle("{handle}");
        post.setTitle("{title}");
        post.setSummary("{summary}");
        post.setContent("{content}");

        BlogPostSerializer serializer = new BlogPostSerializer(post);

        System.out.println(serializer.createPayload());*/
    }
}
