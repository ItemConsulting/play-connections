package no.item.play.connections.utils;

import no.item.play.connections.models.Article;

import java.text.MessageFormat;

public class AtomBulider {
    private static String TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<entry xmlns=\"http://www.w3.org/2005/Atom\" xmlns:app=\"http://www.w3.org/2007/app\" xmlns:snx=\"http://www.ibm.com/xmlns/prod/sn\" xmlns:thr=\"http://purl.org/syndication/thread/1.0\">" +
            "    <title type=\"text\">{0}</title>" +
            "    <summary type=\"html\">{1}</summary>" +
            "    <content type=\"html\">{2}</content>" +
            "</entry>";

    public static String create(Article article){
        return MessageFormat.format(TEMPLATE, article.title, article.summary, article.content);
    }
}
