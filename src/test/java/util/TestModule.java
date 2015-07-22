package util;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import no.item.play.connections.annotations.ServerUrl;
import play.libs.ws.WSClient;

public class TestModule extends AbstractModule {
    private Config config;

    public TestModule(Config config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(ServerUrl.class).to(config.getString("connections.server.host"));
        TestWSClient wsClient = new TestWSClient(
                config.getString("connections.server.username"),
                config.getString("connections.server.password")
        );
        bind(WSClient.class).toInstance(wsClient);
    }
}