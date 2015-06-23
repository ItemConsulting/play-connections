package modules;

import com.google.inject.AbstractModule;
import endpoint.ConnectionsEndpoint;
import endpoint.ConnectionsEndpointProvider;
import endpoint.ConnectionsUrl;
import play.Configuration;

public class DefaultModule extends AbstractModule {

    private final Configuration config;

    public DefaultModule(Configuration config) {
        this.config = config;
    }

    @Override
    protected void configure() {

        bindConstant().annotatedWith(ConnectionsUrl.class).to(config.getString("connections.url"));

        bind(ConnectionsEndpoint.class).toProvider(ConnectionsEndpointProvider.class);


    }
}
