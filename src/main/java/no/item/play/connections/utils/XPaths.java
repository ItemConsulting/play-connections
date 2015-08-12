package no.item.play.connections.utils;

import com.google.common.base.Charsets;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import play.Logger;
import play.libs.XPath;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Optional;

public class XPaths {
    private static String XPATH_FIRST_IMAGE = "//img[1]/@src";
    private static String BLOG =  "//*[@itemtype='https://schema.org/Blog']";
    private static String BLOG_POSTING =  "//*[@itemtype='https://schema.org/BlogPosting']";

    public static class BlogEntry {
        public static String URL = BLOG_POSTING + "//a[@itemprop='wsUrl']/@href";
        public static String AUTHOR = BLOG_POSTING + "//*[@itemprop='author']";
        public static String DESCRIPTION =  "//*[@itemprop='description']";
    }


    public static class Blog {
        public static String TITLE = "//*[@itemprop='name']";
    }

    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static Optional<String> getImageURL(final String source){
        return getNodeValue(clean(source), XPATH_FIRST_IMAGE).map(String::trim);
    }

    public static Optional<String> getNodeValue(final String source, final String xpath) {
        return get(noBr(source), xpath).map(Node::getNodeValue);
    }

    public static Optional<String> getXml(final String source, final String xpath) {
        return get(noBr(source), xpath).flatMap(XPaths::nodeToString);
    }

    public static Optional<String> getTextContent(final String source, final String xpath) {
        return get(noBr(source), xpath).map(Node::getTextContent);
    }

    public static Optional<Node> get(final String source, final String xpath) {
        try {
            return Optional.ofNullable(XPath.selectNode(xpath, docByString(source)));
        } catch (Exception e) {
            Logger.error("Couldn't find xpath={}", source);
            return Optional.empty();
        }
    }

    private static Optional<String> nodeToString(Node node) {
        try {
            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(node), new StreamResult(writer));
            return Optional.ofNullable(writer.toString());
        } catch(TransformerException e) {
            return Optional.empty();
        }
    }

    private static Document docByString(String source) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        String cleaned = clean(source);
        return builder.parse(new ByteArrayInputStream(cleaned.getBytes(Charsets.UTF_8)));
    }

    private static String clean(String str){
        return "<div>" + str.replaceAll("&nbsp;", " ").replaceAll("<br >", "").replaceAll("<br>","").trim() + "</div>";
    }

    private static String noBr(String str) {
        return str.replaceAll("<br>","");
    }
}