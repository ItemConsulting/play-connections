package parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.libs.Json;
import play.libs.XPath;
import java.util.*;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;

public class Parser {
    private String listXPath;
    private List<Attribute> attributes = new ArrayList<>();

    public Parser(String listXPath, List<Attribute> attributes) {
        this.listXPath = listXPath;
        this.attributes = attributes;
    }

    /**
     * Creates a builder for parsing xml-documents
     * @param listXPath The xpath to the first element in a list.
     * @return A ParserBuilder
     */
    public static ParserBuilder list(String listXPath){
        return new ParserBuilder(listXPath);
    }

    public <A> List<A> parse(Document doc, final Class<A> aClass){
        return parse(doc).stream()
                .map(json -> Json.fromJson(json, aClass))
                .collect(toList());
    }

    public List<JsonNode> parse(Document doc){
        List<JsonNode> result = new ArrayList<>();

        NodeList list = XPath.selectNodes(listXPath, doc);

        for (int i=0; i < list.getLength(); i++) {
            result.add(parseToJson(list.item(i)));
        }

        return result;
    }

    private ObjectNode parseToJson(Node item){
        ObjectNode json = Json.newObject();

        for(Attribute attr : attributes) {
            String value = XPath.selectText(attr.xpath, item);
            // applies transformer if it exists
            Optional<String> formatted = attr.transform.map(f -> f.apply(value));
            json.put(attr.key, formatted.orElse(value));
        }

        return json;
    }

    /**
     * An attribute is a mapping between a object attribute name, and the xpath to read from the xml
     */
    public static class Attribute {
        public final String key;
        public final String xpath;
        public final Optional<Function<String, String>> transform;

        public Attribute(String key, String xpath, Function<String, String> transform) {
            this.key = key;
            this.xpath = xpath;
            this.transform = Optional.ofNullable(transform);
        }
    }
}