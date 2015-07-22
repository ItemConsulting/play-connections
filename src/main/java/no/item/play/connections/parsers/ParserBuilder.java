package no.item.play.connections.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ParserBuilder {
    private final String listXPath;
    private List<Parser.Attribute> attributes = new ArrayList<>();
    private List<Parser.XObject> objects = new ArrayList<>();

    public ParserBuilder(String listXPath){
        this.listXPath = listXPath;
    }

    public ParserBuilder attribute(String key, String xpath, Function<String, String> transform) {
        attributes.add(new Parser.Attribute(key, xpath, transform));
        return this;
    }

    public ParserBuilder attribute(String key, String xpath){
        attribute(key, xpath, null);
        return this;
    }

    public ParserBuilder object(String key, Parser parser){
        objects.add(new Parser.XObject(key, parser));
        return this;
    }

    public Parser build(){
        return new Parser(listXPath, attributes, objects);
    }
}