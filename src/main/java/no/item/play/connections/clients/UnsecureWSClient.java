package no.item.play.connections.clients;

import no.item.play.connections.clients.credentials.Credentials;
import play.api.libs.ws.WSClientConfig;
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder;
import play.api.libs.ws.ning.NingWSClientConfig;
import play.api.libs.ws.ning.NingWSClientConfigFactory;
import play.api.libs.ws.ssl.SSLConfigFactory;
import play.libs.ws.WSClient;
import play.libs.ws.ning.NingWSClient;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.concurrent.TimeUnit;
public class UnsecureWSClient extends ConnectionsWSClient {
    @Inject
    public UnsecureWSClient(Provider<Credentials> credentials){
        super(credentials, client());
    }

    private static WSClient client(){
        NingAsyncHttpClientConfigBuilder secureBuilder = new NingAsyncHttpClientConfigBuilder(config());
        return new NingWSClient(secureBuilder.build());
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