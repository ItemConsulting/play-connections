package no.item.play.connections.services;

import no.item.play.connections.annotations.ServerUrl;
import no.item.play.connections.clients.ConnectionsWSClient;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Singleton
public class ImageService {
    private ConnectionsWSClient client;
    private String serverUrl;

    @Inject
    public ImageService(ConnectionsWSClient client, @ServerUrl String serverUrl){
        this.client = client;
        this.serverUrl = serverUrl;
    }

    public Promise<BufferedImage> image(String url){
        return request(url).get().map(this::parseResponse);
    }

    /**
     * Ensures not sending logon criteria to other domain then connecitons.
     * @param url Url to image
     * @return A request that can be used to query for the image
     */
    private WSRequest request(String url){
        return onConnectionsDomain(url) ? client.url(url) : WS.url(url);
    }

    private boolean onConnectionsDomain(String url){
        return url.toLowerCase().contains(serverUrl.toLowerCase());
    }

    public BufferedImage parseResponse(WSResponse response) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(response.asByteArray()));
    }
}
