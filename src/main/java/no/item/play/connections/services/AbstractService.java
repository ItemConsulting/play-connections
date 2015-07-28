package no.item.play.connections.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.nerdforge.xml.parsers.ArrayParser;
import com.nerdforge.xml.parsers.Parser;
import no.item.play.connections.Constants;
import no.item.play.connections.WSException;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WSResponse;

import java.io.IOException;
import java.util.List;

public abstract class AbstractService implements Constants {
    protected F.Function<? super WSResponse, Boolean> validateDeleted(){
        return response -> {
            validate(response);
            return Boolean.TRUE;
        };
    }

    protected F.Function<? super WSResponse, JsonNode> validateAndParse(Parser parser){
        return response -> parser.apply(validate(response).asXml());
    }

    protected <T> F.Function<? super WSResponse, T> validateAndParse(Parser parser, Class<T> type){
        return response -> Json.fromJson(parser.apply(validate(response).asXml()), type);
    }

    protected <T> F.Function<? super WSResponse, List<T>> validateAndParseArray(ArrayParser parser, Class<T> type){
        return response -> toList(parser.apply(validate(response).asXml()), type);
    }

    protected <T> List<T> toList(JsonNode node, Class<T> type) {
        try {
            TypeReference<List<T>> typeRef = new TypeReference<List<T>>() {};
            return Json.mapper().readValue(node.traverse(), typeRef);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    protected WSResponse validate(WSResponse response){
        if(response.getStatus() >= 400){
            throw new WSException(response);
        }

        return response;
    }
}
