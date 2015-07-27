package util;


import no.item.play.connections.clients.ConnectionsWSClient;
import no.item.play.connections.clients.credentials.BasicCredentials;
import no.item.play.connections.clients.credentials.Credentials;
import play.api.libs.ws.WSClientConfig;
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder;
import play.api.libs.ws.ning.NingWSClientConfig;
import play.api.libs.ws.ning.NingWSClientConfigFactory;
import play.api.libs.ws.ssl.SSLConfigFactory;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.ning.NingWSClient;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
public class TestWSClient implements WSClient {
    private WSClient client;

    public TestWSClient(String username, String password){
        NingAsyncHttpClientConfigBuilder secureBuilder = new NingAsyncHttpClientConfigBuilder(config());
        NingWSClient ningWSClient = new NingWSClient(secureBuilder.build());
        Credentials credentials = new BasicCredentials(username, password);
        client = new ConnectionsWSClient(() -> credentials, ningWSClient);
    }

    @Override
    public Object getUnderlying() {
        return client;
    }

    @Override
    public WSRequest url(String url) {
        return client.url(url);
    }

    @Override
    public void close() {
        client.close();
    }

    /**
     * A configuration that doesn't require strict atherance to ssl-rules, to make testing easier.
     * @return A WSClient config
     */
    private static NingWSClientConfig config(){
        scala.Option<String> noneString = scala.None$.empty();
        WSClientConfig wsClientConfig = new WSClientConfig(
                Duration.apply(120, TimeUnit.SECONDS), // connectionTimeout
                Duration.apply(120, TimeUnit.SECONDS), // idleTimeout
                Duration.apply(120, TimeUnit.SECONDS), // requestTimeout
                true, // followRedirects
                false, // useProxyProperties
                noneString, // userAgent
                true, // compressionEnabled / enforced
                SSLConfigFactory.defaultConfig());

        return NingWSClientConfigFactory.forClientConfig(wsClientConfig);
    }
}