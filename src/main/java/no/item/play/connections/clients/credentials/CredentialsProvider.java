package no.item.play.connections.clients.credentials;
import javax.inject.Provider;

public class CredentialsProvider implements Provider<Credentials> {
    @Override
    public Credentials get() {
        return new BasicCredentials("", "");
    }
}