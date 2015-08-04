package no.item.play.connections.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.nerdforge.xml.parsers.ArrayParser;
import com.nerdforge.xml.parsers.Parser;
import no.item.play.connections.Constants;
import no.item.play.connections.WSException;
import no.item.play.connections.clients.ConnectionsWSRequest;
import no.item.play.connections.models.Atom;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.io.IOException;
import java.util.List;

public abstract class AbstractService implements Constants {
    private Logger.ALogger logger = Logger.of("connections-play");

    protected F.Function<? super WSResponse, Boolean> validateDeleted(){
        return response -> {
            validate(response);
            return Boolean.TRUE;
        };
    }

    protected <A> ConnectionsWSRequest builder(WSRequest request, ArrayParser parser){
        return new ConnectionsWSRequest<List<A>>(request, validateAndParseArray(parser));
    }

    protected F.Function<? super WSResponse, JsonNode> validateAndParse(Parser parser){
        return response -> parser.apply(validate(response).asXml());
    }

    protected <T> F.Function<? super WSResponse, T> validateAndParse(Parser parser, Class<T> type){
        return response -> Json.fromJson(parser.apply(validate(response).asXml()), type);
    }

    protected <T> F.Function<? super WSResponse, List<T>> validateAndParseArray(ArrayParser parser){
        return response -> toList(parser.apply(validate(response).asXml()));
    }

    protected <T> List<T> toList(JsonNode node) {
        try {
            // TODO Use Json.mapper();
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new Jdk8Module());
            mapper.registerModule(new JSR310Module());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return (List<T>) mapper.readValue(node.traverse(), typeref());
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    protected WSResponse validate(WSResponse response){
        logger.debug("validate ws-response: status=[{}], response.isEmpty=[{}]", response.getStatus(), response.getBody().isEmpty());
        if(response.getStatus() >= 400){
            throw new WSException(response);
        }

        return response;
    }

    protected abstract TypeReference<?> typeref();
}
