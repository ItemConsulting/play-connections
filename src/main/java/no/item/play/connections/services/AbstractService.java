package no.item.play.connections.services;

import no.item.play.connections.WSException;
import play.libs.ws.WSResponse;

public abstract class AbstractService {
    protected WSResponse validate(WSResponse response){
        if(response.getStatus() >= 400){
            throw new WSException(response);
        }

        return response;
    }
}
