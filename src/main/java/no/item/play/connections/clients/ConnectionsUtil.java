package no.item.play.connections.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.nerdforge.xml.parsers.ArrayParser;
import no.item.play.connections.WSException;
import org.w3c.dom.Document;
import play.Logger;
import play.libs.F;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.io.IOException;

public class ConnectionsUtil<A> {
    private static Logger.ALogger logger = Logger.of("connections-play");

    public static <A> ConnectionsWSRequest<A> builder(WSRequest request, ArrayParser parser, TypeReference<A> typeReference){
        return new ConnectionsWSRequest<>(request, validateAndParseArray(parser, typeReference));
    }

    private static <A> F.Function<? super WSResponse, A> validateAndParseArray(ArrayParser parser, TypeReference<A> typeReference){
        return response -> {
            Document document = validate(response).asXml();
            ArrayNode node = parser.apply(document);
            return (A) fromJson(node, typeReference);
        };
    }

    private static <A> A fromJson(JsonNode node, TypeReference<A> typeref) {
        try {
            return mapper().readValue(node.traverse(), typeref);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper mapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JSR310Module());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    private static WSResponse validate(WSResponse response){
        logger.debug("validate ws-response: status=[{}], response.isEmpty=[{}]", response.getStatus(), response.getBody().isEmpty());
        if(response.getStatus() >= 400){
            throw new WSException(response);
        }

        return response;
    }
}
