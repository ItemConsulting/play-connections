package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ParserBuilder {
    private final String listXPath;
    private List<Parser.Attribute> attributes = new ArrayList<>();

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

    public Parser build(){
        return new Parser(listXPath, attributes);
    }
}