package no.item.play.connections.clients;

import play.libs.F.*;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

public class ConnectionsWSRequest<T> {
    private WSRequest request;
    private Function<? super WSResponse, T> mapper;

    public ConnectionsWSRequest(WSRequest request, Function<? super WSResponse, T> mapper){
        this.request = request;
        this.mapper = mapper;
    }

    public ConnectionsWSRequest<T> size(Integer size){
        request.setQueryParameter("ps", Integer.toString(size));
        return this;
    }

    public ConnectionsWSRequest<T> page(Integer page){
        request.setQueryParameter("page", Integer.toString(page));
        return this;
    }

    public Promise<T> get(){
        return request.get().<T>map(mapper);
    }
}
